package com.example.gymdigital.service;

import com.example.gymdigital.entities.Clase;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.repositories.claseRepository;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class claseService {

    private final claseRepository claseRepository;
    private final usuarioRepository usuarioRepository;

    //constructor de claseService
    public claseService (claseRepository claseRepository , usuarioRepository usuarioRepository) {
        this.claseRepository = claseRepository;
        this.usuarioRepository = usuarioRepository;

    }

    /*METODOS CRUD */

    //CREAR O GUARDAR USUARIOS

    public Clase crearClase(Clase clase) {

        if (clase.getEntrenador() == null || clase.getEntrenador().getId() == null) {
            throw new IllegalArgumentException("Error: Debes asignar un entrenador a la clase.");
        }

        Long idEntrenador = clase.getEntrenador().getId();

        // 1. Buscamos el usuario en la base de datos
        Usuario usuarioEntrenador = usuarioRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("Error: El usuario con ID " + idEntrenador + " no existe."));

        // 2. Validamos que este usuario realmente tenga el rol de ENTRENADOR
        // (Ajusta "ENTRENADOR" según cómo tengas declarado tu Enum)
        if (!usuarioEntrenador.getRol().name().equals("ENTRENADOR")) {
            throw new IllegalArgumentException("Error: El usuario seleccionado no es un entrenador válido.");
        }

        //Validacion de clase duplicadas
        if (claseRepository.existsByNombreIgnoreCase(clase.getNombre())) {
            throw new RuntimeException("Error: Ya existe una clase registrada con el nombre " + clase.getNombre());
        }

        // 3. Vinculamos y guardamos
        clase.setEntrenador(usuarioEntrenador);


        return claseRepository.save(clase);
    }

    //leer usuario
    public List<Clase> buscarClase() {

        return claseRepository.findAll();
    }

    //buscar por id
    public Optional<Clase> buscarClasePorId(Long id) {

        return claseRepository.findById((id));
    }

    //ELIMINAr
    public void eliminarClase(Clase clase) {
        claseRepository.delete(clase);
    }

    //eliminar usuairo por id
    public void eliminarClasePorId(Long id) {
        claseRepository.deleteById(id);
    }

    //actualizar usuario
    public Clase actualizarClase(Long id, Clase claseActualizada) {
        return claseRepository.findById(id).map(claseExistente -> {
            claseExistente.setNombre(claseActualizada.getNombre());
            claseExistente.setCapacidadMaxima(claseActualizada.getCapacidadMaxima());
            claseExistente.setFechaInicio(claseActualizada.getFechaInicio());
            claseExistente.setFechaFin(claseActualizada.getFechaFin());
            claseExistente.setEntrenador(claseActualizada.getEntrenador());

            return claseRepository.save(claseExistente);
        }).orElseThrow(() -> new RuntimeException("Clase no encontrada"));
    }
}
