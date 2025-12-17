package com.reparaya.model;

public class SistemaConfig {
    private String tema;
    private String idioma;
    private String formatoFecha;
    private boolean animaciones;
    private boolean sonidos;

    public SistemaConfig(String tema, String idioma, String formatoFecha, boolean animaciones, boolean sonidos) {
        this.tema = tema;
        this.idioma = idioma;
        this.formatoFecha = formatoFecha;
        this.animaciones = animaciones;
        this.sonidos = sonidos;
    }

    public String getTema() { return tema; }
    public String getIdioma() { return idioma; }
    public String getFormatoFecha() { return formatoFecha; }
    public boolean isAnimaciones() { return animaciones; }
    public boolean isSonidos() { return sonidos; }
}