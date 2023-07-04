package br.com.basis.employee.domain.mappers;

import br.com.basis.employee.domain.dto.ServidorDTO;
import br.com.basis.employee.domain.model.Servidor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ServidorMapper {
    List<ServidorDTO> entityToDtoList(List<Servidor> entities);

    ServidorDTO entityToDto(Servidor entity);

    Servidor dtoToEntity(ServidorDTO dto);
}
