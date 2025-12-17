package com.reparaya.service;

import com.reparaya.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {

    // SE PASA A PRODUCTOS, ESTE ACTUA COMO "BASE DE DATOS"
//    private List<Producto> listaProductos = new ArrayList<>();
//
//    public InventarioService() {
//        // Datos dummy iniciales para probar el diseño
//        listaProductos.add(new Producto(1L, "Pantalla iPhone 12", "Proveedor A", "Pantallas", 5, 3, 100.0, 120.0));
//        listaProductos.add(new Producto(2L, "Batería Li-ion", "Proveedor B", "Baterías", 2, 5, 35.0, 45.0)); // Stock bajo
//        listaProductos.add(new Producto(3L, "Cable USB-C", "Accesorios", "Accesorios", 15, 10, 8.0, 12.0));
//        listaProductos.add(new Producto(4L, "Teclado MacBook", "Proveedor C", "Componentes", 0, 2, 70.0, 85.0)); // Agotado
//        listaProductos.add(new Producto(5L, "Pantalla Samsung S23", "Proveedor A", "Pantallas", 8, 3, 90.0, 110.0));
//    }
//
//    public List<Producto> obtenerTodos() {
//        return listaProductos;
//    }
//
//    public void guardar(Producto producto) {
//        // Simulación de auto-incremento de ID
//        if (producto.getId() == null) {
//            producto.setId((long) (listaProductos.size() + 1));
//        }
//        // Lógica simple: Si el ID ya existe, actualizamos (no implementado aquí por simplicidad), sino agregamos
//        listaProductos.add(producto);
//    }
//
//    // KPIs para el dashboard superior
//    public int contarTotal() { return listaProductos.size(); }
//    public int contarAgotados() { return (int) listaProductos.stream().filter(p -> "Agotado".equals(p.getEstado())).count(); }
//    public int contarStockBajo() { return (int) listaProductos.stream().filter(p -> "Stock bajo".equals(p.getEstado())).count(); }
//    public double calcularValorInventario() {
//        return listaProductos.stream().mapToDouble(p -> p.getStock() * p.getPrecioCosto()).sum();
//    }
}