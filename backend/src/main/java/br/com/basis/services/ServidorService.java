package br.com.basis.services;

import br.com.basis.constant.CategoriaConstants;
import br.com.basis.dto.ServidorDTO;
import br.com.basis.entities.Servidor;
import br.com.basis.repository.ServidorRepository;
import br.com.basis.services.exceptions.DataBaseException;
import br.com.basis.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static br.com.basis.constant.CategoriaConstants.ID_NOT_FOUND;
import static br.com.basis.constant.ServidorConstants.INTEGRITY_VIOLATION;

@Service
@AllArgsConstructor
public class ServidorService {

    private final ServidorRepository servidorRepository;

    @Transactional
    public Page<ServidorDTO> findAllPaged(String nome, String matricula, Pageable pageable)  {
        Page<Servidor> list = servidorRepository.find(nome, matricula, pageable);
        return list.map(ServidorDTO::new);
    }

    @Transactional
    public ServidorDTO findById(Long id) {
        Optional<Servidor> obj = servidorRepository.findById(id);
        Servidor entity = obj.orElseThrow(() -> new ResourceNotFoundException(CategoriaConstants.ENTITY_NOT_FOUND));
        return new ServidorDTO(entity);
    }

    @Transactional
    public ServidorDTO insert(ServidorDTO dto) {
        Servidor entity = new Servidor();
        copyDtoEntity(dto, entity);
        entity = servidorRepository.save(entity);
        return new ServidorDTO(entity);
    }

    @Transactional
    public ServidorDTO update(Long id, ServidorDTO dto) {
        try {
            Servidor entity = servidorRepository.getOne(id);
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

/*    @Transactional
    public void excluirItensEmBloco(List<Long> ids) {
        try {
            servidorRepository.deleteAll(ids);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ID_NOT_FOUND + ids);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(INTEGRITY_VIOLATION);
        }
    }*/

    public boolean verificarItensExistentes(List<Long> ids) {
        return servidorRepository.existsAnyByIdIn(ids);
    }

    private void copyDtoEntity(ServidorDTO dto, Servidor entity) {
        entity.setNome(dto.getNome());
        entity.setMatricula(dto.getMatricula());
        entity.setLotacao(dto.getLotacao());
        entity.setCategoria(dto.getCategoria());

    }

}
