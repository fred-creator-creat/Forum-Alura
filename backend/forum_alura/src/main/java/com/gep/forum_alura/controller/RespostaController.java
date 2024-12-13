package com.gep.forum_alura.controller;


import com.gep.forum_alura.domain.resposta.AtualizarRespostaDTO;
import com.gep.forum_alura.domain.resposta.CriarRespostaDTO;
import com.gep.forum_alura.domain.resposta.Resposta;
import com.gep.forum_alura.domain.resposta.DetalheRespostaDTO;
import com.gep.forum_alura.domain.topico.Estado;
import com.gep.forum_alura.domain.topico.Topico;
import com.gep.forum_alura.domain.topico.TopicoRepository;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import com.gep.forum_alura.domain.resposta.RespostaRepository;
import com.gep.forum_alura.domain.resposta.validacoes.atualizar.ValidarRespostaAtualizada;
import com.gep.forum_alura.domain.resposta.validacoes.criar.ValidarRespostaCriada;

@RestController
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Resposta", description = "Somente um pode ser a solução para o tópico.")
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    List<ValidarRespostaCriada> criarValidadores;

    @Autowired
    List<ValidarRespostaAtualizada> atualizarValidadores;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra uma nova resposta no banco de dados, vinculada a um usuário e tópico existentes.")
    public ResponseEntity<DetalheRespostaDTO> criarResposta(@RequestBody @Valid CriarRespostaDTO criarRespostaDTO, UriComponentsBuilder uriBuilder){

        criarValidadores.forEach(v -> v.validate(criarRespostaDTO));

        Usuario usuario = usuarioRepository.getReferenceById(criarRespostaDTO.usuarioId());
        Topico topico = topicoRepository.findById(criarRespostaDTO.topicoId()).get();

        var resposta = new Resposta(criarRespostaDTO, usuario, topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalheRespostaDTO(resposta));

    }


    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Lê todas as respostas do tópico dado.")
    public ResponseEntity<Page<DetalheRespostaDTO>> lerRespostaDeTopico(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Direction.ASC)Pageable pageable, @PathVariable Long topicoId){
        var pagina = respostaRepository.findAllByTopicoId(topicoId, pageable).map(DetalheRespostaDTO::new);
       return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{nomeUsuario}")
    @Operation(summary = "Lê todas as respostas do nome de usuário fornecido.")
    public ResponseEntity<Page<DetalheRespostaDTO>> lerRespostasDeUsuarios(@PageableDefault(size = 5, sort = {"ultimaAtualizacao"}, direction = Direction.ASC) Pageable pageable, @PathVariable Long usuarioId){
        var pagina = respostaRepository.findAllByUsuarioId(usuarioId, pageable).map(DetalheRespostaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lê uma única resposta pelo seu ID.")
    public ResponseEntity<DetalheRespostaDTO> lerUmaResposta(@PathVariable Long id){
        Resposta resposta = respostaRepository.getReferenceById(id);

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
    @Operation(summary = "Atualiza a mensagem da resposta, a solução ou o estado da resposta.")
    public ResponseEntity<DetalheRespostaDTO> atualizarResposta(@RequestBody @Valid AtualizarRespostaDTO atualizarRespostaDTO, @PathVariable Long id){
        atualizarValidadores.forEach(v -> v.validate(atualizarRespostaDTO, id));
        Resposta resposta = respostaRepository.getReferenceById(id);
        resposta.atualizarResposta(atualizarRespostaDTO);

        if(atualizarRespostaDTO.solucao()){
            var temaResolvido = topicoRepository.getReferenceById(resposta.getTopico().getId());
            temaResolvido.setEstado(Estado.CLOSED);
        }

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


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina uma resposta pelo seu ID.")
    public ResponseEntity<?> apagarResposta(@PathVariable Long id){
        Resposta resposta = respostaRepository.getReferenceById(id);
        resposta.eliminarResposta();
        return ResponseEntity.noContent().build();
    }

}



