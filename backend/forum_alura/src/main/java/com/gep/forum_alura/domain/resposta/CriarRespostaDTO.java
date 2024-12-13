package com.gep.forum_alura.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarRespostaDTO(
        @NotBlank String mensagem,
        @NotNull Long usuarioId,
        @NotNull long topicoId
) {
}

