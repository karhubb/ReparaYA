package com.reparaya.controller;

import com.reparaya.model.Servicio;
import com.reparaya.model.Usuario;
import com.reparaya.service.ServicioService;
import com.reparaya.repository.ServicioRepository; // Usamos repo directo para consultas rápidas
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // 1. Seguridad de Sesión
        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (logueado == null) return "redirect:/";

        model.addAttribute("nombreUsuario", logueado.getNombre());
        model.addAttribute("rolUsuario", logueado.getRol());

        // 2. Obtener todos para calcular KPIs (Contadores)
        List<Servicio> todos = servicioRepository.findAll();

        long pendientes = todos.stream().filter(s -> "Pendiente".equals(s.getEstado()) || "Registrado".equals(s.getEstado())).count();
        long proceso = todos.stream().filter(s -> "En Reparación".equals(s.getEstado()) || "En Diagnóstico".equals(s.getEstado())).count();
        long completados = todos.stream().filter(s -> "Entregado".equals(s.getEstado()) || "Listo".equals(s.getEstado())).count();

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("proceso", proceso);
        model.addAttribute("completados", completados);

        // 3. Tabla de "Últimos Servicios"
        model.addAttribute("servicios", servicioRepository.findTop5ByOrderByIdDesc());

        return "dashboard";
    }
}