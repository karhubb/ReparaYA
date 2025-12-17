package com.reparaya.controller;

import com.reparaya.model.EmpresaConfig;
import com.reparaya.model.Usuario;
import com.reparaya.service.ConfiguracionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configService;

    @GetMapping("/configuracion")
    public String verConfiguracion(HttpSession session, Model model) {
        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado == null) return "redirect:/";

        model.addAttribute("nombreUsuario", logueado.getNombre());

        // Enviamos SOLO los datos de la empresa
        model.addAttribute("empresa", configService.obtenerEmpresa());
        model.addAttribute("personal", configService.obtenerPersonal());

        return "configuracion";
    }

    @PostMapping("/configuracion/empresa")
    public String guardar(@ModelAttribute EmpresaConfig empresa) {
        configService.guardarEmpresa(empresa);
        return "redirect:/configuracion";
    }

    @PostMapping("/configuracion/personal")
    public String guardarPersonal(@ModelAttribute Usuario usuario) {
        configService.guardarUsuario(usuario);
        return "redirect:/configuracion";
    }

    @GetMapping("/configuracion/personal/eliminar/{id}")
    public String eliminarPersonal(@PathVariable Integer id) {
        configService.eliminarUsuario(id);
        return "redirect:/configuracion";
    }
}