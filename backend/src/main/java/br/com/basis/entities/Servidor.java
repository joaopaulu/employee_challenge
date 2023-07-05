package br.com.basis.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static br.com.basis.constant.ServidorConstants.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_servidor")
public class Servidor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = EMPTY_NAME)
    @Size(max = 60)
    private String nome;

    @NotEmpty(message = EMPTY_MAT)
    @Pattern(regexp = "\\d+", message = ONLY_NUMBERS)
    private String matricula;

    @NotEmpty(message = EMPTY_NAME)
    private String lotacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
