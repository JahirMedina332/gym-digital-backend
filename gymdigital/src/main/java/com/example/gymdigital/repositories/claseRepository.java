package com.example.gymdigital.repositories;

import com.example.gymdigital.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface claseRepository extends JpaRepository<Clase, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}
