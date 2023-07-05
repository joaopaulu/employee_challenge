package br.com.basis.services;

import br.com.basis.dto.RoleDTO;
import br.com.basis.entities.Role;
import br.com.basis.repository.RoleRepository;
import br.com.basis.services.exceptions.DataBaseException;
import br.com.basis.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAllPaged(Pageable pageable) {
        Page<Role> list = repository.findAll(pageable);
        return list.map(RoleDTO::new);
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Optional<Role> obj = repository.findById(id);
        Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RoleDTO(entity);
    }

    private String posto;
    private String nomeGuerra;
    private LocalDate dtNascimento;
    private String identidade;

    @Transactional
    public RoleDTO insert(RoleDTO dto) {
        Role entity = new Role();
        entity.setAuthority(dto.getAuthority());
        entity = repository.save(entity);
        return new RoleDTO(entity);
    }

    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        try {
            Role entity = repository.getOne(id);
            entity.setAuthority(dto.getAuthority());
            entity = repository.save(entity);
            return new RoleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
