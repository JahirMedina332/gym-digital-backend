package com.example.gymdigital.controller;

import com.example.gymdigital.DTOs.UsuarioResponseDTO;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.service.usuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class usuarioController {

    private final usuarioService usuarioService;


    public usuarioController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.crearUsuario(usuario);
        UsuarioResponseDTO respuesta = new UsuarioResponseDTO(
                usuarioNuevo.getId(),
                usuarioNuevo.getNombre(),
                usuarioNuevo.getEmail(),
                usuarioNuevo.getRol().name(),
                usuarioNuevo.getFechaRegistro()
        );
        return new ResponseEntity<>(respuesta,HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        return  ResponseEntity.ok(usuarioService.buscarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {

        return usuarioService.buscarUsuarioPorId((id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }


}
