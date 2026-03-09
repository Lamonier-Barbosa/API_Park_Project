package com.project.park_api.web.records;

import com.project.park_api.entity.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;

public record UsuarioCreate(
        @NotBlank(message = "O username é obrigatório e não pode ficar em branco.")
        @Email(message = "Formato do e-mail está inválido.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String username,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 3, max = 20, message = "A senha deve ter entre 3 e 20 caracteres.")
        String password)
{

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setUsername(this.username());
        usuario.setPassword(this.password());
        return usuario;
    }
}
