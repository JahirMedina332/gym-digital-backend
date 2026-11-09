package com.example.gymdigital.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservaResponseDTO {

    private Long idReserva;
    private LocalDate fecha;
    private String estado;
    private String nombreCliente;
    private String nombreClase;

}
