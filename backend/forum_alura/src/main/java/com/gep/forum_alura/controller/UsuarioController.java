package com.gep.forum_alura.controller;


import com.gep.forum_alura.domain.usuario.AtualizarUsuarioDTO;
import com.gep.forum_alura.domain.usuario.Usuario;
import com.gep.forum_alura.domain.usuario.CriarUsuarioDTO;
import com.gep.forum_alura.domain.usuario.UsuarioRepository;
import com.gep.forum_alura.domain.usuario.DetalhesUsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import com.gep.forum_alura.domain.usuario.validacoes.atualizar.ValidarAtualizarUsuario;
import com.gep.forum_alura.domain.usuario.validacoes.criar.ValidarCriarUsuario;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Criar tópicos e publicar respostas.")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCriarUsuario> criarValidador;

    @Autowired
    List<ValidarAtualizarUsuario> atualizarValidador;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra um novo usuário no banco de dados.")
    public ResponseEntity<DetalhesUsuarioDTO> criarUsuario(@RequestBody @Valid CriarUsuarioDTO criarUsuarioDTO, UriComponentsBuilder uriBuilder){
        criarValidador.forEach(v -> v.validate(criarUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(criarUsuarioDTO.password());
        Usuario usuario = new Usuario(criarUsuarioDTO, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesUsuarioDTO(usuario));
    }

    @GetMapping("/all")
    @Operation(summary = "Enumera todos os usuários, independentemente do seu estado.")
    public ResponseEntity<Page<DetalhesUsuarioDTO>> lerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"})Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetalhesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista apenas usuários habilitados.")
    public ResponseEntity<Page<DetalhesUsuarioDTO>> lerUsuariosAtivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByEnabledTrue(pageable).map(DetalhesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Lê um único usuário pelo seu nome de usuário.")
    public ResponseEntity<DetalhesUsuarioDTO> lerUmUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        var dadosUsuario = new DetalhesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(dadosUsuario);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Lê um único usuário pelo seu ID.")
    public ResponseEntity<DetalhesUsuarioDTO>lerUmUsuario(@PathVariable Long id){
        Usuario usuario = repository.getReferenceById(id);
        var dadosUsuario = new DetalhesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(dadosUsuario);
    }

    @PutMapping("/{username}")
    @Transactional
    @Operation(summary = "Atualiza a senha de um usuário, o papel (ou função), nome e sobrenome, e-mail ou estado habilitado.")
    public ResponseEntity<DetalhesUsuarioDTO> atualizarUsuario(@RequestBody @Valid AtualizarUsuarioDTO atualizarUsuarioDTO, @PathVariable String username){
        atualizarValidador.forEach(v -> v.validate(atualizarUsuarioDTO));

        Usuario usuario = (Usuario) repository.findByUsername(username);

        if (atualizarUsuarioDTO.password() != null){
            String hashedPassword = passwordEncoder.encode(atualizarUsuarioDTO.password());
            usuario.atualizarUsuarioComPassword(atualizarUsuarioDTO, hashedPassword);

        }else {
            usuario.atualizarUsuario(atualizarUsuarioDTO);
        }

        var dadosUsuario = new DetalhesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(dadosUsuario);
    }

    @DeleteMapping("/{username}")
    @Transactional
    @Operation(summary = "Desabilita um usuário.")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }


}
