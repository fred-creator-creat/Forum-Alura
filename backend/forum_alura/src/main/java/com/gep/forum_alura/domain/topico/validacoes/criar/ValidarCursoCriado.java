package com.gep.forum_alura.domain.topico.validacoes.criar;

import com.gep.forum_alura.domain.curso.CursoRepository;
import com.gep.forum_alura.domain.topico.CriarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCriado implements ValidarTopicoCriado{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(CriarTopicoDTO data) {
        var ExisteCurso = repository.existsById(data.cursoId());
        if(!ExisteCurso){
            throw new ValidationException("Este curso não existe.");
        }

        var cursoHabilitado = repository.findById(data.cursoId()).get().getAtivo();
        if(!cursoHabilitado){
            throw new ValidationException("Este curso não está disponivel neste momento.");
        }
    }
}
