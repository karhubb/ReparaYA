package com.reparaya.web;

import java.time.LocalDate;

// Clase simple para representar una orden de servicio
public class ServiceItem {
    private Long id;
    private String cliente;
    private String dispositivo;
    private String estado; // Pendiente, En reparación, Entregado, etc.
    private LocalDate fecha;

    public ServiceItem(Long id, String cliente, String dispositivo, String estado, LocalDate fecha) {
        this.id = id;
        this.cliente = cliente;
        this.dispositivo = dispositivo;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters necesarios para que Thymeleaf pueda leer los datos
    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public String getDispositivo() { return dispositivo; }
    public String getEstado() { return estado; }
    public LocalDate getFecha() { return fecha; }

    // Helper para CSS: convierte "En reparación" -> "reparacion"
    // Esto sirve para que el HTML sepa qué color ponerle a la etiqueta
    public String getEstadoCss() {
        if (estado == null) return "";
        return estado.toLowerCase()
                .replace(" ", "-")
                .replace("ó", "o")
                .replace("á", "a");
    }
}