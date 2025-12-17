package com.reparaya.controller;

import com.reparaya.model.Usuario;
import com.reparaya.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 1. Mostrar la pantalla de Login al entrar a la app (Ruta raíz /)
    @GetMapping("/")
    public String loginPage(HttpSession session) {
        // Si ya está logueado, lo mandamos directo al dashboard
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/dashboard";
        }
        return "index"; // Carga templates/index.html (Tu Login Blanco)
    }

    // 2. Procesar el formulario (Botón "Ingresar")
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Usuario usuario = usuarioService.autenticar(correo, password);

        if (usuario != null) {
            // LOGIN CORRECTO: Guardamos usuario en sesión y vamos al sistema
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/dashboard";
        } else {
            // LOGIN FALLIDO: Volvemos al login con mensaje de error
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "index";
        }
    }

    // 3. Cerrar Sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}