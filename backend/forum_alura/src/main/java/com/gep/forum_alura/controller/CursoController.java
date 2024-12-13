package com.gep.forum_alura.controller;


import com.gep.forum_alura.domain.curso.Curso;
import com.gep.forum_alura.domain.curso.AtualizarCursoDTO;
import com.gep.forum_alura.domain.curso.CriarCursoDTO;
import com.gep.forum_alura.domain.curso.CursoRepository;
import com.gep.forum_alura.domain.curso.DetalheCursoDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso", description = "Pode pertencer a uma das muitas categorias definidas.")
public class CursoController {

    @Autowired
    private CursoRepository repository;


    //Crear un Topico
    @PostMapping
    @Transactional
    @Operation(summary = "Registrar um novo curso no banco de dados.")
    public ResponseEntity<DetalheCursoDTO> criarTopico(@RequestBody @Valid CriarCursoDTO criarCursoDTO, //"Para indicar a Spring que é um parâmetro, usa-se @RequestBody, e @Valid valida os dados em DadosRegistroMedico sejam válidos e cheguem corretamente."
                                                       UriComponentsBuilder uriBuilder){  // "Gera a URL (URI) a ser retornada onde está o registro criado."
        Curso curso = new Curso(criarCursoDTO); //"criarCursoDTO deve estar definido na classe Curso como um construtor com os parâmetros a serem exibidos."
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{i}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalheCursoDTO(curso));

    }

    //Mostrar todos los cursos
    @GetMapping("/all")
    @Operation(summary = "Lê todos os cursos, independentemente do seu estado.")
    public ResponseEntity<Page<DetalheCursoDTO>> listarCursos(@PageableDefault(size = 5, sort = {"id"})Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetalheCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    //Mostrar cursos Activo
    @GetMapping
    @Operation(summary = "Lista de cursos ativos")
    public ResponseEntity<Page<DetalheCursoDTO>> listarCursosAtivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByAtivoTrue(pageable).map(DetalheCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    //Mostrar curso por Id
    @GetMapping("/{id}")
    @Operation(summary = "Lê um único curso pelo seu ID.")
    public ResponseEntity<DetalheCursoDTO> ListarUmCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        var dadosDoCurso = new DetalheCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getAtivo()
        );
        return ResponseEntity.ok(dadosDoCurso);
    }

    //Actualizar un Curso

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualiza o nome, a categoria ou o estado de um curso.")
    public ResponseEntity<DetalheCursoDTO> atualizarCurso(@RequestBody @Valid AtualizarCursoDTO atualizarCursoDTO, @PathVariable Long id){

        Curso curso = repository.getReferenceById(id);

        curso.atualizarCurso(atualizarCursoDTO);

        var dadosDoCurso = new DetalheCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getAtivo()
        );
        return ResponseEntity.ok(dadosDoCurso);
    }


    //Eliminar curso - Altera o estado de ativo de true para false.
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }

}
