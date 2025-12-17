package com.reparaya.model;

public class EmpresaConfig {
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String sitioWeb;
    private String rfc;
    private String moneda;
    private String margenDefault; // Lo dejamos String para evitar errores de conversión

    public EmpresaConfig() {}

    public EmpresaConfig(String nombre, String email, String telefono, String direccion, String sitioWeb, String rfc, String moneda, String margenDefault) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sitioWeb = sitioWeb;
        this.rfc = rfc;
        this.moneda = moneda;
        this.margenDefault = margenDefault;
    }

    // Getters y Setters OBLIGATORIOS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getMargenDefault() { return margenDefault; }
    public void setMargenDefault(String margenDefault) { this.margenDefault = margenDefault; }
}