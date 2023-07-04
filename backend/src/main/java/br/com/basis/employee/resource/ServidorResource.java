package br.com.basis.employee.resource;

import br.com.basis.employee.domain.dto.ServidorDTO;
import br.com.basis.employee.repository.ServidorRepository;
import br.com.basis.employee.service.ServidorService;
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
public class ServidorResource {

    private final ServidorService servidorService;
    private final ServidorRepository servidorRepository;

    @GetMapping
    public ResponseEntity<Page<ServidorDTO>> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "matricula", defaultValue = "") String matricula,
            Pageable pageable) {
        Page<ServidorDTO> list = servidorService.findAllPaged(nome, matricula, pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> findById(@PathVariable Long id) {
        ServidorDTO dto =  servidorService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ServidorDTO> insert(@RequestBody ServidorDTO dto) {
        dto =  servidorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> update(@PathVariable Long id, @RequestBody ServidorDTO dto) {
        dto =  servidorService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ServidorDTO> delete(@PathVariable Long id) {
        if (!servidorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servidorService.delete(id);
        return ResponseEntity.noContent().build();
    }

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
