package com.example.gymdigital.controller;

import com.example.gymdigital.DTOs.ReservaResponseDTO;
import com.example.gymdigital.entities.Reserva;
import com.example.gymdigital.service.reservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class reservaController {

    private final reservaService reservaService;


    public reservaController(reservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@RequestBody Reserva reserva) {
        Reserva r = reservaService.crearReserva(reserva);

        ReservaResponseDTO respuesta = new ReservaResponseDTO(
                r.getId(),
                r.getFechaReserva(),
                r.getEstado().name(),         // 3° posición: El estado
                r.getUsuario().getNombre(),   // 4° posición: El cliente
                r.getClase().getNombre()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        List<Reserva> reservas = reservaService.buscarReserva();

        List<ReservaResponseDTO> respuesta = reservas.stream()
                .map(r -> new ReservaResponseDTO(
                        r.getId(),
                        r.getFechaReserva(),
                        r.getEstado().name(),
                        r.getUsuario().getNombre(),
                        r.getClase().getNombre()
                ))
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable Long id) {

        return reservaService.buscarReservaPorId((id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReservaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorUsuario(@PathVariable Long usuarioId) {

        List<Reserva>reservas = reservaService.obtenerReservasPorUsuario(usuarioId);

        List<ReservaResponseDTO>respuesta = reservas.stream()
                .map(r -> new ReservaResponseDTO(
                        r.getId(),
                        r.getFechaReserva(),
                        r.getEstado().name(),
                        r.getUsuario().getNombre(),
                        r.getClase().getNombre()
                        )
                )
                .toList();

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable Long id) {
        Reserva reservaCancelada = reservaService.cancelarReserva(id);

        ReservaResponseDTO respuesta = new ReservaResponseDTO(
                reservaCancelada.getId(),
                reservaCancelada.getFechaReserva(),
                reservaCancelada.getEstado().name(),
                reservaCancelada.getUsuario().getNombre(),
                reservaCancelada.getClase().getNombre()
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
