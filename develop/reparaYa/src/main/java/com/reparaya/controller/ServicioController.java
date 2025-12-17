package com.reparaya.controller;

import com.reparaya.model.Servicio;
import com.reparaya.model.Usuario;
import com.reparaya.service.ClienteService;
import com.reparaya.service.ServicioService;
import com.reparaya.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor; // <--- NECESARIO
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder; // <--- NECESARIO
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private UsuarioService usuarioService;

    // =======================================================
    // 🛡️ PROTECCIÓN CONTRA EL ERROR 500
    // Esto evita que el sistema falle si envías campos vacíos
    // =======================================================
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Convierte textos vacíos ("") a null automáticamente
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/servicios")
    public String listarServicios(Model model) {
        List<Servicio> lista = servicioService.listarTodos();
        model.addAttribute("servicios", lista);
        model.addAttribute("clientes", clienteService.listarTodos());

        // Estadísticas blindadas contra nulos
        long pendientes = lista.stream().filter(s -> s.getEstado() != null && "Pendiente".equals(s.getEstado())).count();
        long enProceso = lista.stream().filter(s -> s.getEstado() != null && ("En Reparación".equals(s.getEstado()) || "En Diagnóstico".equals(s.getEstado()))).count();
        long completados = lista.stream().filter(s -> s.getEstado() != null && "Entregado".equals(s.getEstado())).count();

        List<Usuario> tecnicos = usuarioService.listarTodos().stream()
                .filter(u -> "Técnico".equals(u.getRol()))
                .toList();
        model.addAttribute("tecnicos", tecnicos);

        model.addAttribute("countPendientes", pendientes);
        model.addAttribute("countProceso", enProceso);
        model.addAttribute("countCompletados", completados);
        model.addAttribute("clientes", clienteService.listarTodos());

        return "servicios";
    }

    @PostMapping("/servicios/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio,
                                  HttpSession session,
                                  @RequestParam(value = "fileFrente", required = false) MultipartFile fileFrente,
                                  @RequestParam(value = "fileTrasera", required = false) MultipartFile fileTrasera,
                                  @RequestParam(value = "filePantalla", required = false) MultipartFile filePantalla) {

        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado != null) {
            if (servicio.getId() == null || servicio.getId().isEmpty()) {
                servicio.setUsuarioRegistro(logueado);
                servicio.setTecnicoAsignado(logueado);
            }
        }
        // 🔧 Corrección automática: Si los costos vienen vacíos, ponerles 0
        if(servicio.getCostoTotal() == null) servicio.setCostoTotal(0.0);
        if(servicio.getAnticipo() == null) servicio.setAnticipo(0.0);

        servicioService.guardar(servicio, fileFrente, fileTrasera, filePantalla);
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/eliminar/{id}")
    public String eliminarServicio(@PathVariable String id) {
        servicioService.borrar(id);
        return "redirect:/servicios";
    }
}