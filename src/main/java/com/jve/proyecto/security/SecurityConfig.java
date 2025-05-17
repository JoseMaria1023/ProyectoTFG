package com.jve.proyecto.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService; // Inyectado automáticamente

    // Inyección de dependencias
    @Autowired
    public SecurityConfig(@Lazy JWTFilter jwtFilter, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    // Registro de AuthenticationManager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService) // Usamos el servicio combinado
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Habilitar CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para API REST
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Estado sin sesión
                .authorizeRequests(requests -> requests
                        .requestMatchers(
                                HttpMethod.GET,
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/artistas/listar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.DELETE, "/api/auth/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.PUT, "/api/auth/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll() // Permitir autenticación
                        .requestMatchers(HttpMethod.POST, "/api/artistas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/transferencias/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.PUT, "/api/artistas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.DELETE, "/api/artistas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/conciertos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.GET, "/api/conciertos/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.PUT, "/api/conciertos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.DELETE, "/api/conciertos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/giras/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.PUT, "/api/giras/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.DELETE, "/api/giras/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.GET, "/api/giras/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/entradas/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.PUT, "/api/entradas/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/pagos/**").permitAll() // Permitir POST en artistas
                        .requestMatchers(HttpMethod.POST, "/api/asientos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.GET, "/api/asientos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/asientos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.PUT, "/api/asientos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.POST, "/api/zonas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artista
                        .requestMatchers(HttpMethod.PUT, "/api/zonas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.DELETE, "/api/zonas/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.GET, "/api/zonas/**").permitAll() // Permitir POST en artista
                        .requestMatchers(HttpMethod.POST, "/api/recintos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.PUT, "/api/recintos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.DELETE, "/api/recintos/**").hasAuthority("ROLE_ADMIN") // Permitir POST en artistas
                        .requestMatchers(HttpMethod.GET, "/api/artistas/**").permitAll() // Permitir GET en artistas
                        .requestMatchers(HttpMethod.GET, "/api/recintos/**").permitAll() // Permitir GET en artistas
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll() // Permitir registro
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll() // Permitir login
                        .anyRequest().authenticated()); // Requiere autenticación para cualquier otra petición
    
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Añadir filtro JWT para validación
    
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:80")); // Origen permitido
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permitir cualquier cabecera
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Registrar configuración CORS
        return source;
    }
}
