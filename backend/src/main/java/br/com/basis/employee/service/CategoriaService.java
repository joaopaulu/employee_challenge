package br.com.basis.employee.service;

import br.com.basis.employee.config.Mapper;
import br.com.basis.employee.domain.dto.CategoriaDTO;
import br.com.basis.employee.domain.mappers.CategoriaMapper;
import br.com.basis.employee.domain.model.Categoria;
import br.com.basis.employee.repository.CategoriaRepository;
import br.com.basis.employee.service.exception.DataBaseException;
import br.com.basis.employee.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.basis.employee.constant.CategoriaConstants.*;

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
        Categoria entity = obj.orElseThrow(() -> new ResourceNotFoundException(ENTITY_NOT_FOUND));
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = Mapper.factory(CategoriaMapper.class).dtoToEntity(dto);
        entity = categoriaRepository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        try {
            Categoria entity = Mapper.factory(CategoriaMapper.class).dtoToEntity(dto);
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
}
