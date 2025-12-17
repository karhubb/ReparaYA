package com.reparaya.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "GASTO")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String categoria; // Inventario, Servicios públicos, Herramientas, Operativos
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private String comprobante;
    private double monto;

    public Gasto() {}

    public Gasto(Long id, LocalDate fecha, String categoria, String descripcion, String comprobante, double monto) {
        this.id = id;
        this.fecha = fecha;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.comprobante = comprobante;
        this.monto = monto;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getComprobante() { return comprobante; }
    public void setComprobante(String comprobante) { this.comprobante = comprobante; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    // Helper CSS para Categorías (Badges)
    public String getCategoriaCss() {
        if (categoria == null) return "gray";
        switch (categoria) {
            case "Inventario": return "purple";
            case "Servicios públicos": return "orange";
            case "Herramientas": return "blue";
            case "Operativos": return "red";
            default: return "gray";
        }
    }
}
