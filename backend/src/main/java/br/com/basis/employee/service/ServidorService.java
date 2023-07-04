package br.com.basis.employee.service;

import br.com.basis.employee.config.Mapper;
import br.com.basis.employee.domain.dto.ServidorDTO;
import br.com.basis.employee.domain.mappers.ServidorMapper;
import br.com.basis.employee.domain.model.Servidor;
import br.com.basis.employee.repository.ServidorRepository;
import br.com.basis.employee.service.exception.DataBaseException;
import br.com.basis.employee.service.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.basis.employee.constant.CategoriaConstants.ID_NOT_FOUND;
import static br.com.basis.employee.constant.ServidorConstants.ENTITY_NOT_FOUND;
import static br.com.basis.employee.constant.ServidorConstants.INTEGRITY_VIOLATION;

@Service
@AllArgsConstructor
public class ServidorService {

    private final ServidorRepository servidorRepository;

    @Transactional
    public Page<ServidorDTO> findAllPaged(String nome, String matricula, Pageable pageable) {
        Page<Servidor> list = servidorRepository.find(nome, matricula, pageable);
        var listConvert = Mapper.factory(ServidorMapper.class).entityToDtoList(list.toList());
        return new PageImpl<>(listConvert, list.getPageable(), list.getTotalElements());
    }

    @Transactional
    public ServidorDTO findById(Long id) {
        Optional<Servidor> obj = servidorRepository.findById(id);
        Servidor entity = obj.orElseThrow(() -> new ResourceNotFoundException(ENTITY_NOT_FOUND));
        return new ServidorDTO(entity);
    }

    @Transactional
    public ServidorDTO insert(ServidorDTO dto) {
        Servidor entity = new Servidor();
        copyDtoEntity(dto, entity);
        entity = servidorRepository.save(entity);
        return Mapper.factory(ServidorMapper.class).entityToDto(entity);
    }

    @Transactional
    public ServidorDTO update(Long id, ServidorDTO dto) {
        try {
            Servidor entity = servidorRepository.getReferenceById(id);
            copyDtoEntity(dto, entity);
            entity = servidorRepository.save(entity);
            return new ServidorDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            servidorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(INTEGRITY_VIOLATION);
        }
    }

    @Transactional
    public void excluirItensEmBloco(List<Long> ids) {
        try {
            servidorRepository.deleteAllById(ids);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + ids);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(INTEGRITY_VIOLATION);
        }
    }

    public boolean verificarItensExistentes(List<Long> ids) {
        return servidorRepository.existsAnyByIdIn(ids);
    }

    private void copyDtoEntity(ServidorDTO dto, Servidor entity) {
        entity.setNome(dto.getNome());
        entity.setMatricula(dto.getMatricula());
        entity.setLotacao(dto.getLotacao());
        entity.setFoto(dto.getFoto());
        entity.setCategoria(dto.getCategoria());

    }

}
