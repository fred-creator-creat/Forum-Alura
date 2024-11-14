package com.gep.forum_alura.domain.usuario;

public record DetalhesUsuarioDTO(
        Long id,
        String username,
        Role role,
        String nome,
        String sobrenome,
        String email,
        Boolean enabled
) {

    public DetalhesUsuarioDTO(Usuario usuario){
        this(usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getEnabled()
                );
    }
}

