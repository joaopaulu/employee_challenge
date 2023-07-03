package br.com.basis.employee.service;

import br.com.basis.employee.domain.dto.CategoriaDTO;
import br.com.basis.employee.domain.model.Categoria;
import br.com.basis.employee.repository.CategoriaRepository;
import br.com.basis.employee.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import br.com.basis.employee.service.exception.DataBaseException;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Page<CategoriaDTO> findAllPaged(Pageable pageable) {
        Page<Categoria> list = categoriaRepository.findAll(pageable);
        return list.map(CategoriaDTO::new);
    }

    @Transactional
    public CategoriaDTO findById(Long id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        Categoria entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setDescricao(dto.getDescricao());
        entity = categoriaRepository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        try {
            Categoria entity = categoriaRepository.getOne(id);
            entity.setDescricao(dto.getDescricao());
            entity = categoriaRepository.save(entity);
            return new CategoriaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
