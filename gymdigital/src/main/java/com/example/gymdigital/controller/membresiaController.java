package com.example.gymdigital.controller;

import com.example.gymdigital.DTOs.membresiaResponseDTO;
import com.example.gymdigital.entities.Membresia;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.service.membresiaService;
import com.example.gymdigital.service.usuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membresias")
public class membresiaController {

    private final membresiaService membresiaService;


    public membresiaController(membresiaService membresiaService) {
        this.membresiaService = membresiaService;
    }

    @PostMapping
    public ResponseEntity<membresiaResponseDTO> crearMembresia(@RequestBody Membresia memebresia) {

        Membresia m = membresiaService.crearMembresia(memebresia);
        membresiaResponseDTO respuesta = new membresiaResponseDTO(
                m.getId(),
                m.getUsuario().getId(),
                m.getEstadoMembresia().name(),
                m.getFechaInicio(),
                m.getFechaFin(),
                m.getUsuario().getNombre()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<membresiaResponseDTO>> listarMembresia() {
        List<Membresia> membresias = membresiaService.buscarMembresias();


        List<membresiaResponseDTO> respuesta = membresias.stream()
                .map(m -> new membresiaResponseDTO(
                        m.getId(),
                        m.getUsuario().getId(),
                        m.getEstadoMembresia().name(),
                        m.getFechaInicio(),
                        m.getFechaFin(),
                        m.getUsuario().getNombre()
                ))
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membresia> buscarMembresiaPorId(@PathVariable Long id) {

        return membresiaService.buscarMembresiaPorId((id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMembresia(@PathVariable Long id) {
        membresiaService.eliminarMembresiaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<membresiaResponseDTO> actualizarMembresia(@PathVariable Long id, @RequestBody Membresia membresia) {
        Membresia m = membresiaService.actualizarMembresia(id, membresia);

        membresiaResponseDTO respuesta = new membresiaResponseDTO(
                m.getId(),
                m.getUsuario().getId(), // <-- Añadido
                m.getEstadoMembresia().name(),
                m.getFechaInicio(),
                m.getFechaFin(),
                m.getUsuario().getNombre()
        );
        return ResponseEntity.ok(respuesta);
    }


}
