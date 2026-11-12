package com.example.gymdigital.controller;


import com.example.gymdigital.repositories.claseRepository;
import com.example.gymdigital.repositories.membresiaRepository;
import com.example.gymdigital.repositories.reservaRepository;
import com.example.gymdigital.repositories.usuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final usuarioRepository usuarioRepository;
    private final claseRepository claseRepository;
    private final membresiaRepository membresiaRepository;
    private final reservaRepository reservaRepository;

    public DashboardController(usuarioRepository usuarioRepository,
                               claseRepository claseRepository,
                               membresiaRepository membresiaRepository,
                               reservaRepository reservaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.claseRepository = claseRepository;
        this.membresiaRepository = membresiaRepository;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Datos 100% reales desde PostgreSQL
        stats.put("usuariosActivos", usuarioRepository.count());
        stats.put("clasesTotales", claseRepository.count());
        stats.put("membresiasActivas", membresiaRepository.count());
        stats.put("reservasTotales", reservaRepository.count());

        return stats;
    }
}