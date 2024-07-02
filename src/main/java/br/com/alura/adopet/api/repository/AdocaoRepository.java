package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    List<Adocao> findAllById(Long id);

    @Query("SELECT a FROM Adocao a WHERE a.tutor_id = :id AND a.status = :status")
    List<Adocao> findAdocoesAprovadasFromTutor(Long id, StatusAdocao status);

}
