package com.reparaya.service;

import com.reparaya.model.Producto;
import com.reparaya.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/productos/";

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Guardar con soporte de FOTO
    public void guardar(Producto producto, MultipartFile archivoFoto) {
        Producto guardado = productoRepository.save(producto);

        if (archivoFoto != null && !archivoFoto.isEmpty()) {
            try {
                Path ruta = Paths.get(UPLOAD_DIR);
                if (!Files.exists(ruta)) Files.createDirectories(ruta);

                String nombreFoto = guardado.getId() + "_" + archivoFoto.getOriginalFilename();
                Files.copy(archivoFoto.getInputStream(), ruta.resolve(nombreFoto), StandardCopyOption.REPLACE_EXISTING);

                guardado.setFoto(nombreFoto);
                productoRepository.save(guardado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public long contarTotal() {
        return productoRepository.count();
    }

    public long contarAgotados() {
        return listarTodos().stream().filter(p -> p.getStock() == 0).count();
    }

    public long contarStockBajo() {
        return listarTodos().stream()
                .filter(p -> p.getStock() > 0 && p.getStock() <= p.getStockMinimo())
                .count();
    }

    public double calcularValorInventario() {
        return listarTodos().stream()
                .mapToDouble(p -> p.getPrecioCosto() * p.getStock())
                .sum();
    }
}