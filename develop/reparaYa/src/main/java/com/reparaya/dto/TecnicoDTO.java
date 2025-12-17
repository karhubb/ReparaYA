package com.reparaya.dto;

public class TecnicoDTO {
    private String nombre;
    private long serviciosCompletados;
    private String iniciales;

    public TecnicoDTO(String nombre, long serviciosCompletados) {
        this.nombre = nombre;
        this.serviciosCompletados = serviciosCompletados;
        // Generar iniciales automáticamente (Ej: "Mario Pérez" -> "MP")
        if (nombre != null && !nombre.isEmpty()) {
            String[] parts = nombre.split(" ");
            this.iniciales = parts.length > 1 ?
                    ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase() :
                    ("" + parts[0].charAt(0)).toUpperCase();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getServiciosCompletados() {
        return serviciosCompletados;
    }

    public void setServiciosCompletados(long serviciosCompletados) {
        this.serviciosCompletados = serviciosCompletados;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }
}