package com.gep.forum_alura.domain.usuario;

public record AtualizarUsuarioDTO(
   String password,
   Role role,
   String nome,
   String sobrenome,
   String email,
   Boolean enabled

) {
}


