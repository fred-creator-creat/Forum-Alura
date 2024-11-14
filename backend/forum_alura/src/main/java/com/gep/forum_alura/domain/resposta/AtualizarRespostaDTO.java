package com.gep.forum_alura.domain.resposta;

public record AtualizarRespostaDTO(
        String mensagem, Boolean solucao, Boolean apagado
) {
}
