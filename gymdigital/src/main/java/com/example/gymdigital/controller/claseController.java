package com.example.gymdigital.controller;

import com.example.gymdigital.DTOs.ClaseResponseDTO;
import com.example.gymdigital.entities.Clase;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.service.claseService;
import com.example.gymdigital.service.usuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clase")
public class claseController {

    private final claseService claseService;


    public claseController(claseService claseService) {
        this.claseService = claseService;
    }

    @PostMapping
    public ResponseEntity<ClaseResponseDTO> crearClase(@RequestBody Clase clase) {
        Clase c = claseService.crearClase(clase);
        ClaseResponseDTO respuesta = new ClaseResponseDTO(
                c.getId(),
                c.getNombre(),
               // c.getDescripcion(),
                c.getCapacidadMaxima(),
               // c.getHorario(),
                c.getFechaInicio(),
                c.getFechaFin(),
                c.getEntrenador().getNombre()
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClaseResponseDTO>> listarClases() {

        List<Clase> clases = claseService.buscarClase();
        List<ClaseResponseDTO> respuesta = clases.stream()
                .map(c -> new ClaseResponseDTO(
                        c.getId(),
                        c.getNombre(),
                        // c.getDescripcion(),
                        c.getCapacidadMaxima(),
                        // c.getHorario(),
                        c.getFechaInicio(),
                        c.getFechaFin(),
                        c.getEntrenador().getNombre()
                ))
                .toList();

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> buscarClasePorId(@PathVariable Long id) {

        return claseService.buscarClasePorId((id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClasePorId(id);
        return ResponseEntity.noContent().build();
    }


}
