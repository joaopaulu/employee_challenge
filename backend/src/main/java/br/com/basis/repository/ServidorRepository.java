package br.com.basis.repository;

import br.com.basis.entities.Servidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    @Query("SELECT DISTINCT obj FROM Servidor obj WHERE "
            + "(LOWER(obj.nome) LIKE LOWER(CONCAT('%',:nome,'%'))) AND "
            + "(LOWER(obj.matricula) LIKE CONCAT('%',:matricula,'%')) ")
    Page<Servidor> find(String nome, String matricula, Pageable pageable);

    boolean existsByIdIn(List<Long> ids);

    @Query("SELECT COUNT(s) > 0 FROM Servidor s WHERE s.id IN :ids")
    boolean existsAnyByIdIn(List<Long> ids);
}
