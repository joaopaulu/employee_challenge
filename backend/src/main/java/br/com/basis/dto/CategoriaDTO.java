package br.com.basis.dto;

import br.com.basis.entities.Categoria;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public CategoriaDTO(Categoria entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
    }
}
