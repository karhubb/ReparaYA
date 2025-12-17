package com.reparaya.model;

public class TecnicoDesempeno {
    private String nombre;
    private int serviciosCompletados;
    // Iniciales para el avatar si no hay foto real
    private String iniciales;

    public TecnicoDesempeno(String nombre, int serviciosCompletados, String iniciales) {
        this.nombre = nombre;
        this.serviciosCompletados = serviciosCompletados;
        this.iniciales = iniciales;
    }

    public String getNombre() { return nombre; }
    public int getServiciosCompletados() { return serviciosCompletados; }
    public String getIniciales() { return iniciales; }
}
