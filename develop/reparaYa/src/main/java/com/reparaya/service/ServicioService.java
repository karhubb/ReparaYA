package com.reparaya.service;

import com.reparaya.model.Servicio;
import com.reparaya.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/servicios/";

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio buscarPorId(String id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public void borrar(String id) {
        servicioRepository.deleteById(id);
    }

    public void guardar(Servicio sWeb, MultipartFile fF, MultipartFile fT, MultipartFile fP) {
        if (sWeb.getId() == null || sWeb.getId().isEmpty()) {
            sWeb.setId("SER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            sWeb.setFechaIngreso(LocalDate.now());
            if (sWeb.getEstado() == null) sWeb.setEstado("Registrado");
        } else {
            Servicio original = servicioRepository.findById(sWeb.getId()).orElse(null);
            if (original != null) {
                sWeb.setFechaIngreso(original.getFechaIngreso());

                // También recuperamos las fotos si no se están subiendo unas nuevas
                if (fF == null || fF.isEmpty()) sWeb.setFotoFrente(original.getFotoFrente());
                if (fT == null || fT.isEmpty()) sWeb.setFotoTrasera(original.getFotoTrasera());
                if (fP == null || fP.isEmpty()) sWeb.setFotoPantalla(original.getFotoPantalla());

                // Recuperamos el usuario que lo registró originalmente
                sWeb.setUsuarioRegistro(original.getUsuarioRegistro());
                sWeb.setTecnicoAsignado(original.getTecnicoAsignado());
            }
        }

        Servicio sGuardado = servicioRepository.save(sWeb);

        try {
            Path ruta = Paths.get(UPLOAD_DIR);
            if (!Files.exists(ruta)) Files.createDirectories(ruta);

            boolean cambiosFoto = false;

            if (fF != null && !fF.isEmpty()) {
                String nombre = sGuardado.getId() + "_F_" + fF.getOriginalFilename();
                Files.copy(fF.getInputStream(), ruta.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
                sGuardado.setFotoFrente(nombre);
                cambiosFoto = true;
            }
            if (fT != null && !fT.isEmpty()) {
                String nombre = sGuardado.getId() + "_T_" + fT.getOriginalFilename();
                Files.copy(fT.getInputStream(), ruta.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
                sGuardado.setFotoTrasera(nombre);
                cambiosFoto = true;
            }
            if (fP != null && !fP.isEmpty()) {
                String nombre = sGuardado.getId() + "_P_" + fP.getOriginalFilename();
                Files.copy(fP.getInputStream(), ruta.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
                sGuardado.setFotoPantalla(nombre);
                cambiosFoto = true;
            }

            if (cambiosFoto) {
                servicioRepository.save(sGuardado);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}