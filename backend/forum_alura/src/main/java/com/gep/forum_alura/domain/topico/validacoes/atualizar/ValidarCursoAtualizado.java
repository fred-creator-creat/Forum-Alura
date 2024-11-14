package com.gep.forum_alura.domain.topico.validacoes.atualizar;

import com.gep.forum_alura.domain.curso.CursoRepository;
import com.gep.forum_alura.domain.topico.AtualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoAtualizado implements ValidarTopicoAtualizado{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(AtualizarTopicoDTO data) {
        if(data.cursoId() != null){
            var ExisteCurso = repository.existsById(data.cursoId());
            if (!ExisteCurso){
                throw new ValidationException("Este curso não existe");
            }

            var cursoHabilitado = repository.findById(data.cursoId()).get().getAtivo();
            if(!cursoHabilitado){
                throw new ValidationException("Este curso não está disponivel neste momento.");
            }
        }

    }
}


