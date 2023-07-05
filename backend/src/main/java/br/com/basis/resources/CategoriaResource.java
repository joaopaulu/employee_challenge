package br.com.basis.resources;

import br.com.basis.dto.CategoriaDTO;
import br.com.basis.repository.CategoriaRepository;
import br.com.basis.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping(value = "categorias")
@Tag(name = "Categoria")
public class CategoriaResource {

    private final CategoriaService categoriaService;
    private final CategoriaRepository categoriaRepository;

    @Operation(summary = "Listar todas as Categorias")
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> findAll(Pageable pageable) {
        Page<CategoriaDTO> list = categoriaService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Listar uma Categoria")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        CategoriaDTO dto = categoriaService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Criar uma nova Categoria")
    @PostMapping
    public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto) {
        dto = categoriaService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(summary = "Atualizar uma Categoria")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        dto = categoriaService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Deletar uma Categoria")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> delete(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
