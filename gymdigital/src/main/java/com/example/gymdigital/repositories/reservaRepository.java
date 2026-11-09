package com.example.gymdigital.repositories;

import com.example.gymdigital.entities.Clase;
import com.example.gymdigital.entities.Reserva;
import com.example.gymdigital.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface reservaRepository extends JpaRepository<Reserva, Long> {

    // Spring Boot traducirá esto a: SELECT COUNT(*) FROM reservas WHERE clase_id = ?
    int countByClase(Clase clase);

    // Spring Boot traducirá esto a: Verifica si existe una reserva para este usuario en esta clase
    boolean existsByUsuarioAndClase(Usuario usuario, Clase clase);

    //buscar las reservas de un usuario en especifico

    List<Reserva> findByUsuarioId(Long usuarioId);
}
