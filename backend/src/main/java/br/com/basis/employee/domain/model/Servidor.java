package br.com.basis.employee.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static br.com.basis.employee.constant.ServidorConstants.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
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

    private String foto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;



}
