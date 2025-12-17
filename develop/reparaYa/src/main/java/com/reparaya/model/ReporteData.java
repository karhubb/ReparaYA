package com.reparaya.model;

import com.reparaya.dto.TecnicoDTO;

import java.util.List;
import java.util.Map;

public class ReporteData {
    // KPIs
    private double ingresos;
    private double gastos;
    private double ganancia;
    private int serviciosTotales;

    // Gráficos
    private Map<String, Integer> serviciosPorMes;
    private Map<String, Integer> distribucionEstado;
    private Map<String, Integer> dispositivosTop; // Faltaba en la vista anterior

    // Listas para el Gráfico de Líneas
    private List<String> chartMeses;   // ["Ene", "Feb", ...]
    private List<Double> chartIngresos; // [100.0, 200.0, ...]
    private List<Double> chartGastos;   // [50.0, 80.0, ...]

    // Técnicos
    private List<TecnicoDTO> tecnicos;

    public ReporteData() {}

    // Getters y Setters
    public double getIngresos() { return ingresos; }
    public void setIngresos(double ingresos) { this.ingresos = ingresos; }

    public double getGastos() { return gastos; }
    public void setGastos(double gastos) { this.gastos = gastos; }

    public double getGanancia() { return ganancia; }
    public void setGanancia(double ganancia) { this.ganancia = ganancia; }

    public int getServiciosTotales() { return serviciosTotales; }
    public void setServiciosTotales(int serviciosTotales) { this.serviciosTotales = serviciosTotales; }

    public Map<String, Integer> getServiciosPorMes() { return serviciosPorMes; }
    public void setServiciosPorMes(Map<String, Integer> serviciosPorMes) { this.serviciosPorMes = serviciosPorMes; }

    public Map<String, Integer> getDistribucionEstado() { return distribucionEstado; }
    public void setDistribucionEstado(Map<String, Integer> distribucionEstado) { this.distribucionEstado = distribucionEstado; }

    public Map<String, Integer> getDispositivosTop() { return dispositivosTop; }
    public void setDispositivosTop(Map<String, Integer> dispositivosTop) { this.dispositivosTop = dispositivosTop; }

    public List<TecnicoDTO> getTecnicos() { return tecnicos; }
    public void setTecnicos(List<TecnicoDTO> tecnicos) { this.tecnicos = tecnicos; }

    public List<String> getChartMeses() { return chartMeses; }
    public void setChartMeses(List<String> chartMeses) { this.chartMeses = chartMeses; }
    public List<Double> getChartIngresos() { return chartIngresos; }
    public void setChartIngresos(List<Double> chartIngresos) { this.chartIngresos = chartIngresos; }
    public List<Double> getChartGastos() { return chartGastos; }
    public void setChartGastos(List<Double> chartGastos) { this.chartGastos = chartGastos; }
}