package com.gep.forum_alura.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarTopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull Long usuarioId,
        @NotNull Long cursoId
) {
}
