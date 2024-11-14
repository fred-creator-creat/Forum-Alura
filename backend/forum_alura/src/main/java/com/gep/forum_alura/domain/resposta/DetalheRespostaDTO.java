package com.gep.forum_alura.domain.resposta;

import java.time.LocalDateTime;

public record DetalheRespostaDTO(
        Long id,
        String mensagem,
        LocalDateTime fechaCriacao,
        LocalDateTime ultimaAtualizacao,
        Boolean solucao,
        Boolean apagado,
        Long usuarioId,
        String username,
        Long topicoId,
        String topico
) {

    public DetalheRespostaDTO(Resposta resposta){
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getFechaCriacao(),
                resposta.getUltimaAtualizacao(),
                resposta.getSolucao(),
                resposta.getApagado(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getUsername(),
                resposta.getTopico().getId(),
                resposta.getTopico().getTitulo());
    }

}
