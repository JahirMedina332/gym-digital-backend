package com.example.gymdigital.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private LocalDateTime fechaRegistro;
    private String telefono;
}
