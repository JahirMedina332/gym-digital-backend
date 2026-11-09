package com.example.gymdigital.entities;

import com.example.gymdigital.entities.enums.estadoReserva;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="clase_id", nullable = false)
    private Clase clase;

    @Column(name = "fecha_reserva", nullable = false)
   private LocalDate fechaReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private estadoReserva estado;

    @PrePersist
    protected void onCreate() {
        fechaReserva = LocalDate.now();
    }



}
