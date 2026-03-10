package com.project.park_api.web.records;

import com.project.park_api.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioSenha(
        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @Size(min = 3, max = 20, message = "A senha deve ter entre 3 e 20 caracteres.")
        @NotBlank(message = "A nova senha é obrigatória.")
        String novaSenha,

        @NotBlank(message = "A confirmação de senha é obrigatória.")
        @Size(min = 3, max = 20)
        String confirmaSenha
        ) {

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setPassword(this.senha());
        usuario.setPassword(this.novaSenha());
        usuario.setPassword(this.confirmaSenha());
        return usuario;
    }
}
