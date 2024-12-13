package com.gep.forum_alura.domain.resposta.validacoes.criar;

import com.gep.forum_alura.domain.resposta.CriarRespostaDTO;
import com.gep.forum_alura.domain.topico.Estado;
import com.gep.forum_alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespostaTopicoValida implements ValidarRespostaCriada {

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validate(CriarRespostaDTO data) {
        var topicoExiste = repository.existsById(data.topicoId());

        if (!topicoExiste){
            throw new ValidationException("Este tópico não existe.");
        }

        var topicoAberto = repository.findById(data.topicoId()).get().getEstado();

        if(topicoAberto != Estado.OPEN){
            throw new ValidationException("Este tópico não está aberto.");
        }

    }
}

