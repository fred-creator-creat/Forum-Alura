package com.gep.forum_alura.domain.usuario.validacoes.criar;


import com.gep.forum_alura.domain.usuario.CriarUsuarioDTO;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDuplicado implements ValidarCriarUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarUsuarioDTO data) {
        var usuarioDuplicado = repository.findByUsername(data.username());
        if(usuarioDuplicado != null){
            throw new ValidationException("Este usuario ya existe.");
        }

        var emailDuplicado = repository.findByEmail(data.email());
        if(emailDuplicado != null){
            throw new ValidationException("Este e-mail já existe.");
        }
    }
}

