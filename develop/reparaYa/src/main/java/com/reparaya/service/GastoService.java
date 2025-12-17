package com.reparaya.service;

import com.reparaya.model.Gasto;
import com.reparaya.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> obtenerTodos() {
        return gastoRepository.findAll();
    }

    public void guardar(Gasto gasto) {
        gastoRepository.save(gasto);
    }

    public void eliminar(Long id) {
        gastoRepository.deleteById(id);
    }

    public double calcularTotalGastos() {
        List<Gasto> gastos = gastoRepository.findAll();
        return gastos.stream().mapToDouble(Gasto::getMonto).sum();
    }

    public int contarRegistros() {
        return (int) gastoRepository.count();
    }

    public double calcularPromedio() {
        long count = gastoRepository.count();
        if (count == 0) return 0;
        return calcularTotalGastos() / (count > 0 ? count : 1);
    }

    public Map<String, Integer> obtenerPorcentajesCategoria() {
        List<Gasto> gastos = gastoRepository.findAll();
        double total = gastos.stream().mapToDouble(Gasto::getMonto).sum();

        if (total == 0) return Map.of();

        Map<String, Double> sumas = gastos.stream()
                .collect(Collectors.groupingBy(Gasto::getCategoria, Collectors.summingDouble(Gasto::getMonto)));

        return sumas.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (int) ((e.getValue() / total) * 100)));
    }
}