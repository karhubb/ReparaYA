package com.reparaya.service;

import com.reparaya.dto.TecnicoDTO;
import com.reparaya.model.ReporteData;
import com.reparaya.model.TecnicoDesempeno;
import com.reparaya.repository.GastoRepository;
import com.reparaya.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private GastoRepository gastoRepository;

    public ReporteData obtenerDatosReporte(LocalDate inicio, LocalDate fin) {
        ReporteData data = new ReporteData();

        // --- 1. KPIs Financieros Reales ---
        double ingresos = servicioRepository.obtenerIngresosPorRango(inicio, fin);
        double gastos = gastoRepository.obtenerGastosPorRango(inicio, fin);

        data.setIngresos(ingresos);
        data.setGastos(gastos);
        data.setGanancia(ingresos - gastos);
        data.setServiciosTotales((int) servicioRepository.countByFechaIngresoBetween(inicio, fin));

        // --- 2. Gráfico: Distribución por Estado (Pastel) ---
        Map<String, Integer> dist = new LinkedHashMap<>();
        List<Object[]> resultadosEstado = servicioRepository.contarPorEstado();

        for (Object[] fila : resultadosEstado) {
            String estado = (String) fila[0];
            Long cantidad = (Long) fila[1];
            dist.put(estado != null ? estado : "Sin estado", cantidad.intValue());
        }
        data.setDistribucionEstado(dist);

        // --- 3. Gráfico: Top Dispositivos ---
        Map<String, Integer> topDisp = new LinkedHashMap<>();
        List<Object[]> resultadosTop = servicioRepository.encontrarTopDispositivos();

        for (Object[] fila : resultadosTop) {
            String marca = (String) fila[0];
            String modelo = (String) fila[1];
            Long cantidad = (Long) fila[2];

            String nombreCompleto = (marca != null ? marca : "") + " " + (modelo != null ? modelo : "");
            topDisp.put(nombreCompleto.trim(), cantidad.intValue());
        }
        data.setDispositivosTop(topDisp);

        // --- 4. Gráfico: Servicios por Mes ---
        Map<String, Integer> servMes = new LinkedHashMap<>();
        List<Object[]> resultadosMes = servicioRepository.contarServiciosPorMes();

        String[] nombresMeses = {"", "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

        for (Object[] fila : resultadosMes) {
            Integer mesNumero = ((Number) fila[0]).intValue();
            Long cantidad = ((Number) fila[1]).longValue();

            if (mesNumero >= 1 && mesNumero <= 12) {
                servMes.put(nombresMeses[mesNumero], cantidad.intValue());
            }
        }
        data.setServiciosPorMes(servMes);

        // --- 5. Desempeño Técnicos ---
        List<Object[]> resultadosTecnicos = servicioRepository.contarServiciosPorTecnico();
        List<TecnicoDTO> listaTecnicos = new ArrayList<>();

        for (Object[] res : resultadosTecnicos) {
            // res[0] es el nombre del técnico (String)
            // res[1] es la cantidad de servicios (Long)
            String nombreTec = (res[0] != null) ? (String) res[0] : "Sin Asignar";
            Long cantidad = (res[1] != null) ? (Long) res[1] : 0L;

            listaTecnicos.add(new TecnicoDTO(nombreTec, cantidad));
        }
        data.setTecnicos(listaTecnicos);

        // 6. Grafico Ingresos vs Gastos
        // 6.1. Inicializar listas vacías para los 12 meses
        String[] mesesLabel = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        Double[] ingresosArray = new Double[12]; // [0.0, 0.0, ...]
        Double[] gastosArray = new Double[12];   // [0.0, 0.0, ...]

        // Llenar con ceros para evitar nulls
        for(int i=0; i<12; i++) { ingresosArray[i] = 0.0; gastosArray[i] = 0.0; }

        // 6.2. Llenar Ingresos Reales
        List<Object[]> queryIngresos = servicioRepository.obtenerIngresosPorMes();
        for (Object[] fila : queryIngresos) {
            int mes = ((Number) fila[0]).intValue(); // 1 a 12
            double total = ((Number) fila[1]).doubleValue();
            if (mes >= 1 && mes <= 12) ingresosArray[mes - 1] = total; // mes - 1 porque el array empieza en 0
        }

        // 6.3. Llenar Gastos Reales
        List<Object[]> queryGastos = gastoRepository.obtenerGastosPorMes();
        for (Object[] fila : queryGastos) {
            int mes = ((Number) fila[0]).intValue();
            double total = ((Number) fila[1]).doubleValue();
            if (mes >= 1 && mes <= 12) gastosArray[mes - 1] = total;
        }

        // 6.4. Guardar en el objeto
        data.setChartMeses(List.of(mesesLabel));
        data.setChartIngresos(List.of(ingresosArray));
        data.setChartGastos(List.of(gastosArray));

        return data;
    }
}