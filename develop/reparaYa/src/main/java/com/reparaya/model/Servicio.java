package com.reparaya.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "SERVICIO")
public class Servicio {

    @Id
    @Column(length = 50)
    private String id;

    // Datos Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Datos Dispositivo
    @Column(name = "tipo_dispositivo")
    private String tipoDispositivo;
    private String marca;
    private String modelo;
    @Column(name = "numero_serie")
    private String numeroSerie;
    private String color;
    @Column(name = "contrasena_dispositivo")
    private String contrasena;

    // Diagnóstico
    private String falla;
    @Column(length = 500)
    private String observaciones;
    private String prioridad;

    // Estado y Finanzas
    private String estado;
    @Column(name = "costo_total")
    private Double costoTotal;
    private Double anticipo;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    // Evidencia (Fotos)
    @Column(name = "foto_frente")
    private String fotoFrente;
    @Column(name = "foto_trasera")
    private String fotoTrasera;
    @Column(name = "foto_pantalla")
    private String fotoPantalla;

    // Relación con el Técnico (quien repara)
    @ManyToOne
    @JoinColumn(name = "tecnico_asignado")
    private Usuario tecnicoAsignado;

    // Relación con quien registró (quien estaba en mostrador)
    @ManyToOne
    @JoinColumn(name = "usuario_registro")
    private Usuario usuarioRegistro;

    public Servicio() {}

    // --- GETTERS Y SETTERS (IMPRESCINDIBLES) ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }


    public String getTipoDispositivo() { return tipoDispositivo; }
    public void setTipoDispositivo(String tipoDispositivo) { this.tipoDispositivo = tipoDispositivo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getFalla() { return falla; }
    public void setFalla(String falla) { this.falla = falla; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(Double costoTotal) { this.costoTotal = costoTotal; }

    public Double getAnticipo() { return anticipo; }
    public void setAnticipo(Double anticipo) { this.anticipo = anticipo; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getFotoFrente() { return fotoFrente; }
    public void setFotoFrente(String fotoFrente) { this.fotoFrente = fotoFrente; }

    public String getFotoTrasera() { return fotoTrasera; }
    public void setFotoTrasera(String fotoTrasera) { this.fotoTrasera = fotoTrasera; }

    public String getFotoPantalla() { return fotoPantalla; }
    public void setFotoPantalla(String fotoPantalla) { this.fotoPantalla = fotoPantalla; }

    public Cliente getCliente() {  return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Usuario getTecnicoAsignado() { return tecnicoAsignado; }
    public void setTecnicoAsignado(Usuario tecnicoAsignado) { this.tecnicoAsignado = tecnicoAsignado; }

    public Usuario getUsuarioRegistro() { return usuarioRegistro; }
    public void setUsuarioRegistro(Usuario usuarioRegistro) { this.usuarioRegistro = usuarioRegistro; }
}