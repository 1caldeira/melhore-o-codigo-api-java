package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    List<Adocao> findAllById(Long id);
    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

    @Query("SELECT a FROM Adocao a WHERE a.tutor.id = :id AND a.status = :status")
    List<Adocao> findAdocoesAprovadasFromTutor(@Param("id") Long tutorId, @Param("status") StatusAdocao status);


}
