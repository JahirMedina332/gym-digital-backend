package com.example.gymdigital.repositories;

import com.example.gymdigital.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import tools.jackson.databind.ser.jdk.UUIDSerializer;

import java.util.Optional;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    //buscar por correro a un usuario
    Optional<Usuario> findByEmail(String email);
}
