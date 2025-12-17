package com.reparaya.service;

import com.reparaya.model.Cliente;
import com.reparaya.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/clientes/";

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(String id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void guardar(Cliente cliente, MultipartFile ineFrente, MultipartFile ineReverso) {
        if (cliente.getId() == null || cliente.getId().isEmpty()) {
            cliente.setId(UUID.randomUUID().toString().substring(0, 8));
        }

        Cliente clienteGuardado = clienteRepository.save(cliente);

        try {
            Path rutaUpload = Paths.get(UPLOAD_DIR);
            if (!Files.exists(rutaUpload)) Files.createDirectories(rutaUpload);

            boolean huboCambios = false;

            if (ineFrente != null && !ineFrente.isEmpty()) {
                String nombreFrente = clienteGuardado.getId() + "_frente_" + ineFrente.getOriginalFilename();
                Files.copy(ineFrente.getInputStream(), rutaUpload.resolve(nombreFrente), StandardCopyOption.REPLACE_EXISTING);
                clienteGuardado.setFotoIneFrente(nombreFrente);
                huboCambios = true;
            }

            if (ineReverso != null && !ineReverso.isEmpty()) {
                String nombreReverso = clienteGuardado.getId() + "_reverso_" + ineReverso.getOriginalFilename();
                Files.copy(ineReverso.getInputStream(), rutaUpload.resolve(nombreReverso), StandardCopyOption.REPLACE_EXISTING);
                clienteGuardado.setFotoIneReverso(nombreReverso);
                huboCambios = true;
            }

            if (huboCambios)
                clienteRepository.save(clienteGuardado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(String id) {
        clienteRepository.deleteById(id);
    }
}