package com.reparaya.controller;

import com.reparaya.model.Cliente;
import com.reparaya.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("nuevoCliente", new Cliente()); // Para el formulario vacío
        return "clientes";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente,
                                 @RequestParam("archivoIneFrente") MultipartFile archivoIneFrente,
                                 @RequestParam("archivoIneReverso") MultipartFile archivoIneReverso) {

        // Llamamos a nuestro servicio Java para procesar textos y fotos
        clienteService.guardar(cliente, archivoIneFrente, archivoIneReverso);

        return "redirect:/clientes";
    }
    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable String id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}