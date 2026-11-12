package com.example.gymdigital.entities;

import com.example.gymdigital.entities.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable=false, unique=true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(name ="Fecha_registro")
    private LocalDateTime fechaRegistro;

    //Aclarar dudas de que es lo que significa este codigo
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    


}
