package com.gep.forum_alura.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CriarUsuarioDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Email String email

) {
}
