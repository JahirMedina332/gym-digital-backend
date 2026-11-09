package com.example.gymdigital.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClaseResponseDTO {

    private Long id;
    private String nombre;
   // private String descripcion;
    private Integer capacidadMaxima;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
   // private LocalTime horario;
    private String nombreEntrenador;

}
