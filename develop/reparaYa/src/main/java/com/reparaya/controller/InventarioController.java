package com.reparaya.controller;

import com.reparaya.model.Producto;
import com.reparaya.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class InventarioController {

    private final ProductoService productoService;

    public InventarioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/inventario")
    public String inventario(Model model) {
        // Lista de productos
        model.addAttribute("productos", productoService.listarTodos());

        // KPIs
        model.addAttribute("total", productoService.contarTotal());
        model.addAttribute("agotados", productoService.contarAgotados());
        model.addAttribute("stockBajo", productoService.contarStockBajo());
        model.addAttribute("valorTotal", productoService.calcularValorInventario());

        // Objeto vacío para el formulario de nuevo producto
        model.addAttribute("nuevoProducto", new Producto());

        return "inventario"; // Retorna templates/inventario.html
    }

    @PostMapping("/inventario/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam(value = "archivoFoto", required = false) MultipartFile archivoFoto) {
        productoService.guardar(producto, archivoFoto);
        return "redirect:/inventario";
    }

    @GetMapping("/inventario/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/inventario";
    }
}