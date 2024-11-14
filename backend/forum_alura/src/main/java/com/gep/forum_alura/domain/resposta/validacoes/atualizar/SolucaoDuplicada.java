package com.gep.forum_alura.domain.resposta.validacoes.atualizar;

import com.gep.forum_alura.domain.resposta.AtualizarRespostaDTO;
import com.gep.forum_alura.domain.resposta.Resposta;
import com.gep.forum_alura.domain.topico.Estado;
import com.gep.forum_alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gep.forum_alura.domain.resposta.RespostaRepository;

@Component
public class SolucaoDuplicada implements ValidarRespostaAtualizada{

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(AtualizarRespostaDTO data, Long respostaId) {
       if (data.solucao()){
           Resposta resposta = respostaRepository.getReferenceById(respostaId);
           var topicoResolvido = topicoRepository.getReferenceById(resposta.getTopico().getId());
           if (topicoResolvido.getEstado() == Estado.CLOSED){
               throw new ValidationException("Este tópico já está solucionado.");
           }
       }
    }
}


