package br.com.basis.employee.resource;

import br.com.basis.employee.domain.dto.ServidorDTO;
import br.com.basis.employee.repository.ServidorRepository;
import br.com.basis.employee.service.ServidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.basis.employee.constant.ServidorConstants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/servidores")
@Tag(name = "Servidores")
public class ServidorResource {

    private final ServidorService servidorService;
    private final ServidorRepository servidorRepository;

    @Operation(summary = "Listar todas as Servidores")
    @GetMapping
    public ResponseEntity<Page<ServidorDTO>> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "matricula", defaultValue = "") String matricula,
            Pageable pageable) {
        Page<ServidorDTO> list = servidorService.findAllPaged(nome, matricula, pageable);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Listar um Servidor")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> findById(@PathVariable Long id) {
        ServidorDTO dto =  servidorService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Inserir um Servidor")
    @PostMapping
    public ResponseEntity<ServidorDTO> insert(@RequestBody ServidorDTO dto) {
        dto =  servidorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(summary = "Atualizar um Servidore")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> update(@PathVariable Long id, @RequestBody ServidorDTO dto) {
        dto =  servidorService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Deletar um Servidor")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> delete(@PathVariable Long id) {
        if (!servidorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servidorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar varios Servidores")
    @DeleteMapping("/varios")
    public ResponseEntity<String> excluirItensEmBloco(@RequestBody List<Long> ids) {
        if (servidorService.verificarItensExistentes(ids)) {
            servidorService.excluirItensEmBloco(ids);
            return ResponseEntity.ok(DELETE_SUCCESS);
        } else {
            return ResponseEntity.status(NOT_FOUND).body(ID_DELETE_NOT_FOUND);
        }
    }

}
