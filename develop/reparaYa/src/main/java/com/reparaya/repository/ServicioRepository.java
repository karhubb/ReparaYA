package com.reparaya.repository;

import com.reparaya.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, String> {
    // Suma de ingresos
    @Query("SELECT COALESCE(SUM(s.costoTotal), 0) FROM Servicio s")
    Double obtenerIngresosTotales();

    // Agrupar por ESTADO
    // Devuelve: [["Pendiente", 5], ["Entregado", 10], ...]
    @Query("SELECT s.estado, COUNT(s) FROM Servicio s GROUP BY s.estado")
    List<Object[]> contarPorEstado();

    // 3. Top Dispositivos (Marca + Modelo)
    // Devuelve los 5 más comunes ordenados de mayor a menor
    @Query("SELECT s.marca, s.modelo, COUNT(s) FROM Servicio s " +
            "GROUP BY s.marca, s.modelo " +
            "ORDER BY COUNT(s) DESC LIMIT 5")
    List<Object[]> encontrarTopDispositivos();

    // 4. Servicios por MES
    // Extrae el mes de la fecha (asumiendo formato yyyy-MM-dd)
    @Query(value = "SELECT MONTH(fecha_ingreso) as mes, COUNT(*) as cantidad " +
            "FROM servicio " +
            "WHERE fecha_ingreso IS NOT NULL " +
            "GROUP BY MONTH(fecha_ingreso) " +
            "ORDER BY mes ASC", nativeQuery = true)
    List<Object[]> contarServiciosPorMes();

    // 5. Ganancia por mes
    // Devuelve: [ [1, 5000.0], [2, 7500.0] ] (Mes, Total)
    @Query(value = "SELECT MONTH(fecha_ingreso) as mes, SUM(costo_total) as total " +
            "FROM servicio " +
            "WHERE fecha_ingreso IS NOT NULL " +
            "GROUP BY MONTH(fecha_ingreso)", nativeQuery = true)
    List<Object[]> obtenerIngresosPorMes();

    // 6. Desempeño por tecnico
    @Query("SELECT s.tecnicoAsignado.nombre, COUNT(s) FROM Servicio s " +
            "WHERE s.tecnicoAsignado IS NOT NULL AND s.estado = 'Entregado' " +
            "GROUP BY s.tecnicoAsignado.nombre")
    List<Object[]> contarServiciosPorTecnico();

    // Trae los 5 más recientes basados en el ID (los últimos creados)
    List<Servicio> findTop5ByOrderByIdDesc();

    // Vista de ingresos por rangos
    @Query("SELECT COALESCE(SUM(s.costoTotal), 0) FROM Servicio s WHERE s.fechaIngreso BETWEEN :inicio AND :fin")
    Double obtenerIngresosPorRango(LocalDate inicio, LocalDate fin);

    // Conteo filtrado
    long countByFechaIngresoBetween(LocalDate inicio, LocalDate fin);

    // Para el Excel/PDF
    List<Servicio> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);
}
