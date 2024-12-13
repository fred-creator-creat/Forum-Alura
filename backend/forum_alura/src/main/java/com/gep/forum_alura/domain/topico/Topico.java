package com.gep.forum_alura.domain.topico;


import com.gep.forum_alura.domain.curso.Curso;
import com.gep.forum_alura.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity(name = "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    //"Sempre que uma coluna da tabela do banco de dados tenha um nome diferente do da classe, na classe deve-se adicionar @Column com o nome da coluna definido no banco de dados."
    @Column(name="fecha_criacao")
    private LocalDateTime fechaCriacao;

    @Column(name="ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao; //add date now

    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(CriarTopicoDTO criarTopicoDTO, Usuario usuario, Curso curso) {
        this.titulo = criarTopicoDTO.titulo();
        this.mensagem = criarTopicoDTO.mensagem();
        this.fechaCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
        this.estado = Estado.OPEN;
        this.usuario = usuario;
        this.curso = curso;
    }



    public void atualizarTopicoComCurso(AtualizarTopicoDTO atualizarTopicoDTO, Curso curso) {
        if (atualizarTopicoDTO.titulo() != null){
            this.titulo = atualizarTopicoDTO.titulo();
        }
        if (atualizarTopicoDTO.mensagem() != null){
            this.mensagem = atualizarTopicoDTO.mensagem();
        }
        if (atualizarTopicoDTO.estado() != null){
            this.estado = atualizarTopicoDTO.estado();
        }
        if (atualizarTopicoDTO.cursoId() != null){
            this.curso = curso;
        }
        this.ultimaAtualizacao = LocalDateTime.now();

    }

    public void atualizarTopico(AtualizarTopicoDTO atualizarTopicoDTO){
        if (atualizarTopicoDTO.titulo() != null){
            this.titulo = atualizarTopicoDTO.titulo();
        }
        if (atualizarTopicoDTO.mensagem() != null){
            this.mensagem = atualizarTopicoDTO.mensagem();
        }
        if(atualizarTopicoDTO.estado() != null){
            this.estado = atualizarTopicoDTO.estado();
        }
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void eliminarTopico(){
        this.estado = Estado.DELETED;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}

