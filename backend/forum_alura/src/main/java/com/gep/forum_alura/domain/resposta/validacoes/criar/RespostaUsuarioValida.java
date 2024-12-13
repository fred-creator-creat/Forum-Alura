package com.gep.forum_alura.domain.resposta.validacoes.criar;


import com.gep.forum_alura.domain.resposta.CriarRespostaDTO;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespostaUsuarioValida implements ValidarRespostaCriada{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CriarRespostaDTO data) {
        var usuarioExiste = repository.existsById(data.usuarioId());

        if(!usuarioExiste){
            throw new ValidationException("Este usuário não existe");
        }

        var usuarioHabilitado = repository.findById(data.usuarioId()).get().isEnabled();

        if(!usuarioHabilitado){
            throw new ValidationException("Este usuário não está habilitado");
        }
    }
}
