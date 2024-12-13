package com.gep.forum_alura.domain.curso;


//DTO - //"Para especificar quais parâmetros o método vai exibir."
public record DetalheCursoDTO(Long id, String nome, Categoria categoria, Boolean ativo) {//"Define os parâmetros a serem recebidos."

    //Construtor
    public DetalheCursoDTO(Curso curso){
        this(curso.getId(), curso.getName(), curso.getCategoria(), curso.getAtivo());
    }
}


