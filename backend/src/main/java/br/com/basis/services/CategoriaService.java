package br.com.basis.services;

import br.com.basis.dto.CategoriaDTO;
import br.com.basis.entities.Categoria;
import br.com.basis.repository.CategoriaRepository;
import br.com.basis.services.exceptions.DataBaseException;
import br.com.basis.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static br.com.basis.constant.CategoriaConstants.*;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAllPaged(Pageable pageable) {
        Page<Categoria> list = categoriaRepository.findAll(pageable);
        return list.map(CategoriaDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        Categoria entity = obj.orElseThrow(() -> new ResourceNotFoundException(ENTITY_NOT_FOUND));
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        copyDtoEntity(dto, entity);
        entity = categoriaRepository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        try {
            Categoria entity = categoriaRepository.getOne(id);
            copyDtoEntity(dto, entity);
            entity = categoriaRepository.save(entity);
            return new CategoriaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(INTEGRITY_VIOLATION);
        }
    }

    private void copyDtoEntity(CategoriaDTO dto, Categoria entity) {
        entity.setDescricao(dto.getDescricao());
    }
}
