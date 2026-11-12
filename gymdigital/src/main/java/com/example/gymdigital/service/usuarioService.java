package com.example.gymdigital.service;

import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {

    private final usuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public usuarioService(usuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /*METODOS CRUD */
    //CREAR O GUARDAR USUARIOS
    public Usuario crearUsuario(Usuario usuario) {
        if(usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya existe");
        }
        String password = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(password);

        return usuarioRepository.save(usuario);
    }

    //leer usuario
    public List<Usuario> buscarUsuarios() {

        return usuarioRepository.findAll();
    }

    //buscar por id
    public Optional<Usuario> buscarUsuarioPorId(Long id) {

        return usuarioRepository.findById((id));
    }

    //ELIMINAr
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    //eliminar usuairo por id
    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setRol(usuarioActualizado.getRol());

            // Solo actualizamos la contraseña si el administrador escribió una nueva
            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                usuarioExistente.setPassword(usuarioActualizado.getPassword());
            }
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }



}
