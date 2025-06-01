package com.jve.proyecto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Artista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artista implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtista;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellidos;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    private String descripcion;

    @Column(length = 50)
    private String generoMusical;

    @Lob
     @Column(columnDefinition = "LONGTEXT")
    private String foto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ARTISTA"));
    }

    @Override
    public boolean isAccountNonExpired() {
         return true;
    }

    
    @Override
    public boolean isAccountNonLocked() {
         return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
         return true;
    }

    @Override
    public boolean isEnabled() {
         return true;
    }
}
