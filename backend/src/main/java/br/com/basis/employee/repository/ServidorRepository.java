package br.com.basis.employee.repository;

import br.com.basis.employee.domain.model.Servidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    @Query("SELECT DISTINCT obj FROM Servidor obj WHERE "
            + "(LOWER(obj.nome) LIKE LOWER(CONCAT('%',:nome,'%'))) AND "
            + "(LOWER(obj.matricula) LIKE CONCAT('%',:matricula,'%')) ")
    Page<Servidor> find(String nome, int matricula, Pageable pageable);
}
