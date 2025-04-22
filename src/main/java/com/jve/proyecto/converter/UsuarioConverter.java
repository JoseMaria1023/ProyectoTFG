package com.jve.proyecto.converter;

import com.jve.proyecto.dto.UsuarioDTO;
import com.jve.proyecto.entity.Usuario;

public class UsuarioConverter {

    public static UsuarioDTO toDto(Usuario usuario) {
        return ModelMapperUtils.getMapper().map(usuario, UsuarioDTO.class);
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        return ModelMapperUtils.getMapper().map(usuarioDTO, Usuario.class);
    }
}
