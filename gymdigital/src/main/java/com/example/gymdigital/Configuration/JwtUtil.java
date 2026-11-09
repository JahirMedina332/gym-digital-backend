package com.example.gymdigital.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.gymdigital.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY="GymDigitalSecretKeyMuySegura";

    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withSubject(usuario.getEmail())//identificador principal
                .withClaim("id", usuario.getId())//guardamos el id
                .withClaim("rol", usuario.getRol().name())//guardamos el rol
                .withIssuedAt(new Date())//fecha de emision
                .withExpiresAt(new Date (System.currentTimeMillis() + 1000*60*60*24 ))//expira en un dia (24) horas
                .sign(algorithm);//lo firmamos
    }

}
