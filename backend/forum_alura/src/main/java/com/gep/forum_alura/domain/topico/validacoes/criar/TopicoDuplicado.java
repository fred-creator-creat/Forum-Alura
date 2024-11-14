package com.gep.forum_alura.domain.topico.validacoes.criar;

import com.gep.forum_alura.domain.topico.CriarTopicoDTO;
import com.gep.forum_alura.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCriado{

    @Autowired
    private TopicoRepository topicoRepository;


    @Override
    public void validate(CriarTopicoDTO data) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensagem(data.titulo(), data.mensagem());
        if(topicoDuplicado){
            throw new ValidationException("Este topico já existe. Revisa /topicos/" + topicoRepository.findByTitulo(data.titulo()).getId());

        }
    }
}


