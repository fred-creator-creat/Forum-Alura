package com.gep.forum_alura.domain.topico;

import com.gep.forum_alura.domain.curso.Categoria;

import java.time.LocalDateTime;

public record DetalhesTopicoDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime fechaCriacao,
        LocalDateTime ultimaAtualizacao,
        Estado estado,
        String usuario,
        String curso,
        Categoria categoriaCurso

) {

    public DetalhesTopicoDTO(Topico topico) {
        this(topico.getId(),
            topico.getTitulo(),
            topico.getMensagem(),
            topico.getFechaCriacao(),
            topico.getUltimaAtualizacao(),
            topico.getEstado(),
            topico.getUsuario().getUsername(),
            topico.getCurso().getName(),
            topico.getCurso().getCategoria()
                );
    }
}
