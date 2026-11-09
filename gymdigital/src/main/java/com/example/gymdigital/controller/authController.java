package com.example.gymdigital.controller;

import com.example.gymdigital.Configuration.JwtUtil;
import com.example.gymdigital.DTOs.LoginDTO;
import com.example.gymdigital.entities.Usuario;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class authController {

    private final usuarioRepository usuarioRepository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public authController(usuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository ;
        this.passwordEncoder = passwordEncoder ;
        this.jwtUtil = jwtUtil ;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(()-> new RuntimeException("Error: Usuario no encontrado con ese correo."));

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Error: contraseña incorrecta.");
        }
        String token = jwtUtil.generateToken(usuario);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("mensaje", "Login exitoso.");

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }



}
