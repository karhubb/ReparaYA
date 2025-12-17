package com.reparaya.repository;

import com.reparaya.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // Gastos totales
    @Query("SELECT COALESCE(SUM(g.monto), 0) FROM Gasto g")
    Double obtenerGastosTotales();

    // Gastos por mes
    @Query(value = "SELECT MONTH(fecha) as mes, SUM(monto) as total " +
            "FROM gasto " +
            "WHERE fecha IS NOT NULL " +
            "GROUP BY MONTH(fecha)", nativeQuery = true)
    List<Object[]> obtenerGastosPorMes();

    @Query("SELECT COALESCE(SUM(g.monto), 0) FROM Gasto g WHERE g.fecha BETWEEN :inicio AND :fin")
    Double obtenerGastosPorRango(LocalDate inicio, LocalDate fin);
}
