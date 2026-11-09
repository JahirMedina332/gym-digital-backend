package com.example.gymdigital.repositories;

import com.example.gymdigital.entities.Membresia;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.entities.enums.estadoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface membresiaRepository extends JpaRepository<Membresia, Long> {

    boolean existsByUsuarioAndEstadoMembresia(Usuario usuario, estadoMembresia estado);

}
