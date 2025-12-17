package com.reparaya.service;

import com.reparaya.model.Configuracion;
import com.reparaya.model.EmpresaConfig;
import com.reparaya.model.Usuario;
import com.reparaya.repository.ConfiguracionRepository;
import com.reparaya.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfiguracionService {

    @Autowired
    private ConfiguracionRepository repo;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // LEER de la BD
    public EmpresaConfig obtenerEmpresa() {
        List<Configuracion> lista = repo.findAll();
        Map<String, String> mapa = new HashMap<>();

        for (Configuracion c : lista) {
            mapa.put(c.getClave(), c.getValor());
        }

        return new EmpresaConfig(
                mapa.getOrDefault("empresa.nombre", "Repara Ya"),
                mapa.getOrDefault("empresa.email", ""),
                mapa.getOrDefault("empresa.telefono", ""),
                mapa.getOrDefault("empresa.direccion", ""),
                mapa.getOrDefault("empresa.web", ""),
                mapa.getOrDefault("empresa.rfc", ""),
                mapa.getOrDefault("empresa.moneda", "MXN"),
                mapa.getOrDefault("empresa.margen", "30")
        );
    }

    // GUARDAR en la BD
    public void guardarEmpresa(EmpresaConfig dto) {
        guardarClave("empresa.nombre", dto.getNombre());
        guardarClave("empresa.email", dto.getEmail());
        guardarClave("empresa.telefono", dto.getTelefono());
        guardarClave("empresa.direccion", dto.getDireccion());
        guardarClave("empresa.web", dto.getSitioWeb());
        guardarClave("empresa.rfc", dto.getRfc());
        guardarClave("empresa.moneda", dto.getMoneda());
        guardarClave("empresa.margen", dto.getMargenDefault());
    }

    // Método auxiliar para no repetir código
    private void guardarClave(String clave, String valor) {
        Configuracion c = repo.findById(clave).orElse(new Configuracion());
        c.setClave(clave);
        c.setValor(valor);
        c.setCategoria("Empresa");
        repo.save(c);
    }

    public List<Usuario> obtenerPersonal() {
        return usuarioRepository.findAll();
    }

    public void guardarUsuario(Usuario u) {
        if (u.getId() != null) {
            // === EDICIÓN ===
            // Buscamos el original para no perder datos clave
            Usuario original = usuarioRepository.findById(u.getId()).orElse(null);
            if (original != null) {
                u.setFechaRegistro(original.getFechaRegistro());
                u.setEstado(original.getEstado());

                // Opcional: Si quieres que el username se actualice si cambian el nombre,
                // descomenta la línea de abajo. Si no, mantenemos el original.
                // u.setUsername(original.getUsername());

                // Mantenemos la contraseña vieja si no escriben una nueva
                if (u.getPasswordHash() == null || u.getPasswordHash().trim().isEmpty()) {
                    u.setPasswordHash(original.getPasswordHash());
                }

                // Si permitimos actualizar el username al editar:
                generarUsername(u);
            }
        } else {
            // === CREACIÓN ===
            u.setFechaRegistro(java.time.LocalDateTime.now());
            u.setEstado("Activo");

            // Generamos el username automático la primera vez
            generarUsername(u);
        }

        usuarioRepository.save(u);
    }

    private void generarUsername(Usuario u) {
        String nombreLimpio = u.getNombre().trim().toLowerCase().replaceAll("\\s+", "");

        String sufijo = "";
        switch (u.getRol()) {
            case "Administrador":
                sufijo = "_admin";
                break;
            case "Técnico":
                sufijo = "_tec";
                break;
            case "Recepcionista":
                sufijo = "_rec";
                break;
            default:
                sufijo = "_user";
                break;
        }

        u.setUsername(nombreLimpio + sufijo);
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}