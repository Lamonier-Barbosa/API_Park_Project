package com.project.park_api.web.records;

import com.project.park_api.entity.Usuario;

public record UsuarioResponse(Long id, String username, String role) {

    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole().name().substring("ROLE_".length())
        );
    }
}
