package com.gep.forum_alura.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jshell.Snippet;

public record AtualizarTopicoDTO(
        String titulo,
        String mensagem,
        Estado estado,
        Long cursoId
) {
}
