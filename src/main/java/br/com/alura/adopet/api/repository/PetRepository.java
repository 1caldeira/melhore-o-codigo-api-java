package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.adotado = false")
    List<Pet> findByAdotadoFalse();
}
