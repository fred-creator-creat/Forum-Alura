package com.gep.forum_alura.domain.resposta.validacoes.atualizar;

import com.gep.forum_alura.domain.resposta.AtualizarRespostaDTO;

public interface ValidarRespostaAtualizada {

    public void validate(AtualizarRespostaDTO data, Long respostaId);

}
