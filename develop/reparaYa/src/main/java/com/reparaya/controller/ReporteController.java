package com.reparaya.controller;

import com.reparaya.model.Servicio;
import com.reparaya.repository.ServicioRepository;
import com.reparaya.service.ExportService;
import com.reparaya.service.ReporteService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private ExportService exportService;
    @Autowired
    private ServicioRepository servicioRepository;

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/reportes")
    public String reportes(Model model,
                           @RequestParam(required = false) LocalDate fechaInicio,
                           @RequestParam(required = false) LocalDate fechaFin) {
        if (fechaInicio == null) fechaInicio = LocalDate.now().withDayOfMonth(1);
        if (fechaFin == null) fechaFin = LocalDate.now();

        model.addAttribute("data", reporteService.obtenerDatosReporte(fechaInicio, fechaFin));

        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);

        return "reportes";
    }

    // --- DESCARGAR EXCEL ---
    @GetMapping("/reportes/exportar/excel")
    public void descargarExcel(HttpServletResponse response,
                               @RequestParam LocalDate inicio,
                               @RequestParam LocalDate fin) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reporte_" + inicio + "_al_" + fin + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Servicio> lista = servicioRepository.findByFechaIngresoBetween(inicio, fin);
        exportService.exportarExcel(response, lista);
    }

    // --- DESCARGAR PDF ---
    @GetMapping("/reportes/exportar/pdf")
    public void descargarPDF(HttpServletResponse response,
                             @RequestParam LocalDate inicio,
                             @RequestParam LocalDate fin) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reporte_" + inicio + "_al_" + fin + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Servicio> lista = servicioRepository.findByFechaIngresoBetween(inicio, fin);
        exportService.exportarPDF(response, lista);
    }
}