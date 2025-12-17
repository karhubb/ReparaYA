package com.reparaya.controller;

import com.reparaya.model.Gasto;
import com.reparaya.service.GastoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class GastoController {

    private final GastoService gastoService;

    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping("/gastos")
    public String gastos(Model model) {
        model.addAttribute("gastos", gastoService.obtenerTodos());

        // KPIs
        model.addAttribute("totalGastos", gastoService.calcularTotalGastos());
        model.addAttribute("totalRegistros", gastoService.contarRegistros());
        model.addAttribute("promedioMensual", gastoService.calcularPromedio());

        // Datos para gráfico de barras
        Map<String, Integer> porcentajes = gastoService.obtenerPorcentajesCategoria();
        model.addAttribute("pctInventario", porcentajes.getOrDefault("Inventario", 0));
        model.addAttribute("pctServicios", porcentajes.getOrDefault("Servicios públicos", 0));
        model.addAttribute("pctHerramientas", porcentajes.getOrDefault("Herramientas", 0));
        model.addAttribute("pctOperativos", porcentajes.getOrDefault("Operativos", 0));

        // Objeto vacío para formulario
        model.addAttribute("nuevoGasto", new Gasto());

        return "gastos";
    }

    @PostMapping("/gastos/guardar")
    public String guardarGasto(@ModelAttribute Gasto gasto) {
        gastoService.guardar(gasto);
        return "redirect:/gastos";
    }

    @GetMapping("/gastos/eliminar/{id}")
    public String eliminarGasto(@PathVariable Long id) {
        gastoService.eliminar(id);
        return "redirect:/gastos";
    }
}