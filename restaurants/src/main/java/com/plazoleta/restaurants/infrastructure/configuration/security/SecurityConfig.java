package com.plazoleta.restaurants.infrastructure.configuration.security;

import com.plazoleta.restaurants.infrastructure.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.plazoleta.restaurants.infrastructure.configuration.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/restaurantes").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/restaurantes").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/platos").hasAuthority("PROPIETARIO")
                        .requestMatchers(HttpMethod.PUT, "/platos/{dishId}").hasAuthority("PROPIETARIO")
                        .requestMatchers(HttpMethod.GET, "/platos/restaurante/{restaurantId}").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/pedidos").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/pedidos").hasAuthority("EMPLEADO")
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/asignar").hasAuthority("EMPLEADO")
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/marcar-listo").hasAuthority("EMPLEADO")
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/entregar").hasAuthority("EMPLEADO") // NUEVA RUTA
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}