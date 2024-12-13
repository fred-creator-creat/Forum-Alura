package com.gep.forum_alura.domain.usuario.validacoes.atualizar;

import com.gep.forum_alura.domain.usuario.AtualizarUsuarioDTO;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaAtualizacaoUsuario implements ValidarAtualizarUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(AtualizarUsuarioDTO data) {
        if(data.email() != null){
            var emailDuplicado = repository.findByEmail(data.email());
            if(emailDuplicado != null){
                throw new ValidationException("Este e-mail já está em uso.");
            }
        }
    }
}

