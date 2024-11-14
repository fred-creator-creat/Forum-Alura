package com.gep.forum_alura.domain.curso;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public record CriarCursoDTO(@NotBlank String name, @NotNull Categoria categoria) {
}
