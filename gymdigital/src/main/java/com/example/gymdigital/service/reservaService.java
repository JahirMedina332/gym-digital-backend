package com.example.gymdigital.service;

import com.example.gymdigital.entities.Clase;
import com.example.gymdigital.entities.Reserva;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.entities.enums.estadoReserva;
import com.example.gymdigital.repositories.claseRepository;
import com.example.gymdigital.repositories.reservaRepository;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class reservaService {

    private final reservaRepository reservaRepository;
    private final usuarioRepository usuarioRepository;
    private final claseRepository claseRepository;

    public reservaService (reservaRepository reservaRepository , usuarioRepository usuarioRepository, claseRepository claseRepository) {

        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.claseRepository = claseRepository;
    }
    /*METODOS CRUD */
    //CREAR O GUARDAR USUARIOS

    public Reserva crearReserva(Reserva reserva) {
        // 1. Validar que el JSON traiga los IDs de Usuario y Clase
        if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null ||
                reserva.getClase() == null || reserva.getClase().getId() == null) {
            throw new IllegalArgumentException("Error: La reserva debe incluir el ID del usuario y el ID de la clase.");
        }

        Long idUsuario = reserva.getUsuario().getId();
        Long idClase = reserva.getClase().getId();

        Usuario usuarioReal = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Error: El usuario no existe."));

        Clase claseReal = claseRepository.findById(reserva.getClase().getId())
                .orElseThrow(() -> new RuntimeException("Error: La clase no existe."));

        // --- INICIO DE NUEVAS REGLAS DE NEGOCIO ---

        // Regla 1: Evitar reservas duplicadas (el mismo usuario en la misma clase)
        if (reservaRepository.existsByUsuarioAndClase(usuarioReal, claseReal)) {
            throw new RuntimeException("Error: El usuario ya tiene una reserva para esta clase.");
        }

        // Regla 2: Validar capacidad máxima
        int reservasActuales = reservaRepository.countByClase(claseReal);
        if (reservasActuales >= claseReal.getCapacidadMaxima()) {
            throw new RuntimeException("Error: Lo sentimos, la clase de " + claseReal.getNombre() + " ya está llena.");
        }

        // --- FIN DE REGLAS DE NEGOCIO ---

        reserva.setUsuario(usuarioReal);
        reserva.setClase(claseReal);

        return reservaRepository.save(reserva);
    }

    //leer usuario
    public List<Reserva> buscarReserva() {

        return reservaRepository.findAll();
    }

    //buscar por id
    public Optional<Reserva> buscarReservaPorId(Long id) {

        return reservaRepository.findById((id));
    }

    //ELIMINAr
    public void eliminarReserva(Reserva reserva) {
        reservaRepository.delete(reserva);
    }

    //eliminar usuairo por id
    public void eliminarReservaPorId(Long id) {
        reservaRepository.deleteById(id);
    }

    //obtener reservas por un usuario especifico
    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {

        return reservaRepository.findByUsuarioId(usuarioId);
    }

    //cancelar reserva

    public Reserva cancelarReserva(Long IdReserva) {

        Reserva reserva = reservaRepository.findById(IdReserva)
                .orElseThrow(() -> new RuntimeException("Error: La reserva no existe."));

        if (reserva.getEstado() == estadoReserva.CANCELADO){
            throw new RuntimeException("Error: Esta reserva ya se encuentra cancelada.");
        }

        reserva.setEstado(estadoReserva.CANCELADO);
        return reservaRepository.save(reserva);
    }
}
