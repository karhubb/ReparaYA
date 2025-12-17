package com.reparaya.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CONFIGURACION_EMPRESA")
public class Configuracion {

    @Id
    @Column(length = 100)
    private String clave; // Ej: "empresa.nombre"

    @Column(columnDefinition = "TEXT")
    private String valor; // Ej: "Repara Ya"

    private String descripcion;
    private String categoria;

    // Constructor vacío (Obligatorio para JPA)
    public Configuracion() {}

    // Constructor completo
    public Configuracion(String clave, String valor, String categoria) {
        this.clave = clave;
        this.valor = valor;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}