package com.gep.forum_alura.domain.resposta;

import com.gep.forum_alura.domain.topico.Topico;
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
@Table(name = "respostas")
@Entity(name = "Resposta")
@EqualsAndHashCode(of = "id")

public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @Column(name="fecha_criacao")
    private LocalDateTime fechaCriacao; // = LocalDateTime.now();

    @Column(name="ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    private Boolean solucao;
    private Boolean apagado;

    /*
    "Na tabela respostas do banco de dados, temos id_usuario e id_topico, mas ao fazer a relação com @ManyToOne e realizar o @JoinColumn(name = "id_usuario"), onde se especifica com qual usuário a tabela se relaciona, não é necessário definir esses atributos na tabela (eles são criados automaticamente)."
    */


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") //"É criado um campo id_usuario nesta tabela Resposta, o qual terá uma relação com a tabela usuários."
    private Usuario usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topico_id")
    private Topico topico;

    public Resposta(CriarRespostaDTO criarRespostaDTO, Usuario usuario, Topico topico) {
        this.mensagem = criarRespostaDTO.mensagem();
        this.fechaCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
        this.solucao = false;
        this.apagado = false;
        this.usuario = usuario;
        this.topico = topico;
    }

   public void atualizarResposta(AtualizarRespostaDTO atualizarRespostaDTO){
        if(atualizarRespostaDTO.mensagem() != null){
            this.mensagem = atualizarRespostaDTO.mensagem();
        }
        if (atualizarRespostaDTO.solucao() != null){
            this.solucao = atualizarRespostaDTO.solucao();
        }
        this.ultimaAtualizacao = LocalDateTime.now();
   }

   public void eliminarResposta(){
        this.apagado = true;
   }


}


