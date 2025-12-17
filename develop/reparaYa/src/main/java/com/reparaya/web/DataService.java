package com.reparaya.web;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    // Método para obtener la lista de servicios (simulando una base de datos)
    public List<ServiceItem> obtenerUltimosServicios() {
        List<ServiceItem> items = new ArrayList<>();

        // Datos dummy que coinciden con lo que se ve en el video
        items.add(new ServiceItem(1L, "Juan Pérez", "iPhone 13 Pro", "Pendiente", LocalDate.of(2024, 11, 8)));
        items.add(new ServiceItem(2L, "María López", "MacBook Air M2", "En reparación", LocalDate.of(2024, 11, 7)));
        items.add(new ServiceItem(3L, "Carlos Sánchez", "Samsung Galaxy S23", "Entregado", LocalDate.of(2024, 11, 6)));
        items.add(new ServiceItem(4L, "Ana Martínez", "iPad Pro", "En diagnóstico", LocalDate.of(2024, 11, 5)));
        items.add(new ServiceItem(5L, "Roberto Díaz", "PlayStation 5", "En espera de piezas", LocalDate.of(2024, 11, 4)));

        return items;
    }

    // Métodos para los contadores de las tarjetas de arriba
    public int contarPendientes() {
        return 15; // Dato fijo por ahora
    }

    public int contarEnProceso() {
        return 30; // Dato fijo por ahora
    }

    public int contarCompletados() {
        return 32; // Dato fijo por ahora
    }
}