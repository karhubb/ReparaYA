package com.reparaya.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CLIENTE")
public class Cliente {
    // Campos existentes
    @Id
    @Column(length = 50)
    private String id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(length = 20)
    private String telefono;

    // --- NUEVOS CAMPOS (Información) ---
    @Column(length = 100)
    private String correo;
    @Column(columnDefinition = "TEXT")
    private String direccion;
    @Column(columnDefinition = "TEXT")
    private String notas;

    // --- NUEVOS CAMPOS (Fotos INE) ---
    // Aquí solo guardaremos el nombre del archivo, ej: "ine_123_frente.jpg"
    @Column(name = "foto_ine_frente")
    private String fotoIneFrente;
    @Column(name = "foto_ine_reverso")
    private String fotoIneReverso;

    // Constructor vacío necesario
    public Cliente() {}

    // --- GETTERS Y SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public String getFotoIneFrente() { return fotoIneFrente; }
    public void setFotoIneFrente(String fotoIneFrente) { this.fotoIneFrente = fotoIneFrente; }

    public String getFotoIneReverso() { return fotoIneReverso; }
    public void setFotoIneReverso(String fotoIneReverso) { this.fotoIneReverso = fotoIneReverso; }
}