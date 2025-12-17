package com.reparaya.repository;

import com.reparaya.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para buscar usuarios por su nombre de usuario
    Optional<Usuario> findByUsername(String username);
}