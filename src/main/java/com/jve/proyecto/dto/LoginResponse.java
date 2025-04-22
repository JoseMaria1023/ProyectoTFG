package com.jve.proyecto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {
    private String username;
    private List<String> authorities;
    private String token;
    private Long idUser;
}
 
