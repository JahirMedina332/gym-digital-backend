package com.example.gymdigital.service;

import com.example.gymdigital.entities.Membresia;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.entities.enums.estadoMembresia;
import com.example.gymdigital.repositories.membresiaRepository;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class membresiaService {

    private final membresiaRepository membresiaRepository;
    private final usuarioRepository usuarioRepository;

    public membresiaService(membresiaRepository membresiaRepository , usuarioRepository usuarioRepository) {
        this.membresiaRepository = membresiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /*METODOS CRUD */

    //CREAR O GUARDAR USUARIOS

    public Membresia crearMembresia(Membresia membresia) {

        Long idUsuario = membresia.getUsuario().getId();

        Usuario usuarioReal= usuarioRepository.findById(idUsuario).
                orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        membresia.setUsuario(usuarioReal);

        if(membresiaRepository.existsByUsuarioAndEstadoMembresia(usuarioReal, estadoMembresia.ACTIVA)){
            throw new RuntimeException("Error: El usuario " + usuarioReal.getNombre() + " ya cuenta con una membresía ACTIVA actualmente.");
        }
        return membresiaRepository.save(membresia);
    }

    //leer usuario
    public List<Membresia> buscarMembresias() {

        return membresiaRepository.findAll();
    }

    //buscar por id
    public Optional<Membresia> buscarMembresiaPorId(Long id) {

        return membresiaRepository.findById((id));
    }

    //ELIMINAr
    public void eliminarMembresia(Membresia membresia) {
        membresiaRepository.delete(membresia);
    }

    //eliminar usuairo por id
    public void eliminarMembresiaPorId(Long id) {
        membresiaRepository.deleteById(id);
    }

}
