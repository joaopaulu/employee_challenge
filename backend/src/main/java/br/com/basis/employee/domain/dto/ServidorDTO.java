package br.com.basis.employee.domain.dto;

import br.com.basis.employee.domain.model.Categoria;
import br.com.basis.employee.domain.model.Servidor;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServidorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String matricula;
    private String lotacao;
    private String foto;
    private Categoria categoria;

    public ServidorDTO() {
    }

    public ServidorDTO(Long id, String nome, String matricula, String lotacao, String foto, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.lotacao = lotacao;
        this.foto = foto;
        this.categoria = categoria;
    }

    public ServidorDTO(Servidor servidor) {
        this.id = servidor.getId();
        this.nome = servidor.getNome();
        this.matricula = servidor.getMatricula();
        this.lotacao = servidor.getLotacao();
        this.foto = servidor.getFoto();
        this.categoria = servidor.getCategoria();
    }

}
