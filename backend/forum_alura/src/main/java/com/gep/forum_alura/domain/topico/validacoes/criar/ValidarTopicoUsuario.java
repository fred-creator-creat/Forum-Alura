package com.gep.forum_alura.domain.topico.validacoes.criar;


import com.gep.forum_alura.domain.topico.CriarTopicoDTO;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCriado{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarTopicoDTO data) {
        var existeUsuario = repository.existsById(data.usuarioId());
        if(!existeUsuario){
            throw new ValidationException("Este usuário não existe.");
        }

        var usuarioHabilitado = repository.findById(data.usuarioId()).get().getEnabled();
        if(!usuarioHabilitado){
            throw new ValidationException("Este usuário foi desabilitado.");
        }

    }
}


