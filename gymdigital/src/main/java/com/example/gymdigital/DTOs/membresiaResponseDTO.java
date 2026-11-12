package com.example.gymdigital.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class membresiaResponseDTO {
     private Long id;
     private Long usuarioId;
     private String estado;
     private LocalDate fechaInicio;
     private LocalDate fechaFin;
     private String nombreCliente;

}
