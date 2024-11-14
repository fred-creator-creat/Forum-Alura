package com.gep.forum_alura.domain.curso;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
@Entity(name = "Curso")
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private Boolean ativo;


    public Curso(CriarCursoDTO criarCursoDTO) {
        this.name = criarCursoDTO.name();
        this.categoria = criarCursoDTO.categoria();
        this.ativo = true; //Automaticamente o curso estará ativo.
    }


    public void atualizarCurso(AtualizarCursoDTO atualizarCursoDTO) {

        if(atualizarCursoDTO.name() != null){
            this.name = atualizarCursoDTO.name();
        }
        if (atualizarCursoDTO.categoria() != null){
            this.categoria = atualizarCursoDTO.categoria();
        }
        if (atualizarCursoDTO.ativo() != null){
            this.ativo = atualizarCursoDTO.ativo();
        }
    }

    public void eliminarCurso(){
        this.ativo = false;
    }

}
