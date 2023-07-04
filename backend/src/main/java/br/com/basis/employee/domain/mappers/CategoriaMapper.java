package br.com.basis.employee.domain.mappers;

import br.com.basis.employee.domain.dto.CategoriaDTO;
import br.com.basis.employee.domain.model.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CategoriaMapper {
    List<CategoriaDTO> entityToDtoList(List<Categoria> entities);

    CategoriaDTO entityToDto(Categoria entity);

    Categoria dtoToEntity(CategoriaDTO dto);
}
