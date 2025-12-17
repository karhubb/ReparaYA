package com.reparaya.service;

import com.reparaya.model.Usuario;
import com.reparaya.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
            if (usuario.getEstado() == null) usuario.setEstado("Activo");
            // Aquí en el futuro encriptaremos el password_hash
        }
        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario autenticar(String correo, String password) {
        // Buscamos en la lista de usuarios por correo (o puedes cambiarlo a username)
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getCorreo().equals(correo) && u.getPasswordHash().equals(password))
                .findFirst()
                .orElse(null);
    }
}