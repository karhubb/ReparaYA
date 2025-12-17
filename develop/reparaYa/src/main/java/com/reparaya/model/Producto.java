package com.reparaya.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String proveedor;
    private String categoria; // Pantallas, Baterías, Accesorios
    @Column(length = 500) // Le damos espacio para texto largo
    private String descripcion;
    private int stock;
    @Column(name = "stock_minimo")
    private int stockMinimo;
    @Column(name = "precio_costo")
    private double precioCosto;
    @Column(name = "precio_venta")
    private double precioVenta;
    private String foto;
    @Transient
    private String estado; // Normal, Stock bajo, Agotado

    public Producto() {}

    public Producto(Long id, String nombre, String proveedor, String categoria, String descripcion, int stock, int stockMinimo, double precioCosto, double precioVenta) {
        this.id = id;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.estado = calcularEstado();
    }

    // Lógica para determinar estado automáticamente
    private String calcularEstado() {
        if (stock == 0) return "Agotado";
        if (stock <= stockMinimo) return "Stock bajo";
        return "Normal";
    }

    // Calcular margen de ganancia en porcentaje
    public int getMargenPorcentaje() {
        if (precioCosto == 0) return 0;
        return (int) (((precioVenta - precioCosto) / precioCosto) * 100);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; this.estado = calcularEstado(); }
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
    public double getPrecioCosto() { return precioCosto; }
    public void setPrecioCosto(double precioCosto) { this.precioCosto = precioCosto; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public String getEstado() {
        if (stock == 0) return "Agotado";
        if (stock <= stockMinimo) return "Stock bajo";
        return "Normal";
    }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    // Helpers CSS para la vista
    public String getEstadoCss() {
        if (estado == null) return "gray";
        switch (estado) {
            case "Normal": return "green";
            case "Stock bajo": return "orange";
            case "Agotado": return "red";
            default: return "gray";
        }
    }

    public String getCategoriaCss() {
        if (categoria == null) return "gray";
        switch (categoria) {
            case "Pantallas": return "blue";
            case "Baterías": return "yellow";
            case "Accesorios": return "orange";
            case "Componentes": return "purple";
            default: return "gray";
        }
    }
}