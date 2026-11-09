package com.example.gymdigital.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "GymDigitalSecretKeyMuySegura";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
// --- MICRÓFONOS DE PRUEBA ---
        System.out.println("🔍 --- EL GUARDIA ESTÁ REVISANDO ---");
        System.out.println("Intentando entrar a la ruta: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        System.out.println("¿Trae el gafete (Header Authorization)? : " + authHeader);
        // ----------------------------
        // 2. Verificamos que exista y empiece con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try{
                // 3. Verificamos si el token es válido y no ha expirado
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

                String email = jwt.getSubject();
                String rol = jwt.getClaim("rol").asString(); // 1. Extraemos el rol del token
// 2. Le asignamos ese rol al contexto de Spring Security
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Un micrófono de éxito
                System.out.println("✅ Usuario autenticado en Spring Security: " + email + " | Rol: " + rol);

            } catch (Exception e) {

                System.out.println("Token inválido: " + e.getMessage());
            }
        }
        // Continuamos con el flujo normal de la petición
        filterChain.doFilter(request, response);
    }

}
