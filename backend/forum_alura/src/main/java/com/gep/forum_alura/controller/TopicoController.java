package com.gep.forum_alura.controller;


import com.gep.forum_alura.domain.topico.AtualizarTopicoDTO;
import com.gep.forum_alura.domain.topico.Estado;
import com.gep.forum_alura.domain.topico.CriarTopicoDTO;
import com.gep.forum_alura.domain.topico.DetalhesTopicoDTO;
import com.gep.forum_alura.domain.topico.Topico;
import com.gep.forum_alura.domain.topico.TopicoRepository;
import com.gep.forum_alura.domain.curso.Curso;
import com.gep.forum_alura.domain.curso.CursoRepository;
import com.gep.forum_alura.domain.resposta.DetalheRespostaDTO;
import com.gep.forum_alura.domain.resposta.Resposta;
import com.gep.forum_alura.domain.usuario.Usuario;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import com.gep.forum_alura.domain.resposta.RespostaRepository;
import com.gep.forum_alura.domain.topico.validacoes.atualizar.ValidarTopicoAtualizado;
import com.gep.forum_alura.domain.topico.validacoes.criar.ValidarTopicoCriado;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topico", description = "Está vinculado a um curso e usuário específicos.")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    List<ValidarTopicoCriado> criarValidadores;

    @Autowired
    List<ValidarTopicoAtualizado> atualizarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra um novo tópico no banco de dados.")
    public ResponseEntity<DetalhesTopicoDTO> criarTopico(@RequestBody @Valid CriarTopicoDTO criarTopicoDTO, UriComponentsBuilder uriBuilder){
        criarValidadores.forEach(v -> v.validate(criarTopicoDTO));

        Usuario usuario = usuarioRepository.findById(criarTopicoDTO.usuarioId()).get();
        Curso curso = cursoRepository.findById(criarTopicoDTO.cursoId()).get();
        Topico topico = new Topico(criarTopicoDTO, usuario, curso);

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesTopicoDTO(topico));
    }

    @GetMapping("/all")
    @Operation(summary = "Lê todos os tópicos, independentemente do seu estado.")
    public ResponseEntity<Page<DetalhesTopicoDTO>> lerTodosTopicos(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Sort.Direction.DESC)Pageable pageable){
        var pagina = topicoRepository.findAll(pageable).map(DetalhesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista de tópicos abertos e fechados.")
    public ResponseEntity<Page<DetalhesTopicoDTO>> lerTopicosNaoEliminados(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Sort.Direction.DESC) Pageable pageable){
        var pagina = topicoRepository.findAllByEstadoIsNot(Estado.DELETED, pageable).map(DetalhesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lê um único tópico pelo seu ID.")
    public ResponseEntity<DetalhesTopicoDTO> lerUmTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var dadosTopico = new DetalhesTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getFechaCriacao(),
                topico.getUltimaAtualizacao(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(dadosTopico);
    }

    @GetMapping("/{id}/solucao")
    @Operation(summary = "Lê a resposta do tópico marcada como sua solução.")
    public ResponseEntity<DetalheRespostaDTO> lerSolucaoTopico(@PathVariable Long id){
        Resposta resposta = respostaRepository.getReferenceByTopicoId(id);

        var dadosResposta = new DetalheRespostaDTO(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getFechaCriacao(),
                resposta.getUltimaAtualizacao(),
                resposta.getSolucao(),
                resposta.getApagado(),
                resposta.getUsuario().getId(),
                resposta.getUsuario().getUsername(),
                resposta.getTopico().getId(),
                resposta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(dadosResposta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza o título, a mensagem, o estado ou o ID do curso de um tópico.")
    public ResponseEntity<DetalhesTopicoDTO> atualizarTopico(@RequestBody @Valid AtualizarTopicoDTO atualizarTopicoDTO, @PathVariable Long id){
        atualizarValidadores.forEach(v -> v.validate(atualizarTopicoDTO));

        Topico topico = topicoRepository.getReferenceById(id);

        if(atualizarTopicoDTO.cursoId() != null){
            Curso curso = cursoRepository.getReferenceById(atualizarTopicoDTO.cursoId());
            topico.atualizarTopicoComCurso(atualizarTopicoDTO, curso);
        }else{
            topico.atualizarTopico(atualizarTopicoDTO);
        }

        var dadosTopico = new DetalhesTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getFechaCriacao(),
                topico.getUltimaAtualizacao(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(dadosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina um topico.")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }

}






