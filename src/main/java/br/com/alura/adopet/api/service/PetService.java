package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AbrigoRepository abrigoRepository;


    public List<DadosDetalhesPet> listarTodosNaoAdotados() {
        List<Pet> petsDisponiveis = petRepository.findByAdotadoFalse();
        return petsDisponiveis.stream()
                .map(p -> new DadosDetalhesPet(
                        p.getId(),
                        p.getTipo(),
                        p.getNome(),
                        p.getRaca(),
                        p.getIdade())).toList();
    }

    public List<DadosDetalhesPet> listarPorAbrigo(String idOuNome){

        Abrigo abrigo = getAbrigo(idOuNome);
        List<Pet> pets = abrigo.getPets();
        return pets.stream().map(p -> new DadosDetalhesPet(p.getId(),
                                p.getTipo(),
                                p.getNome(),
                                p.getRaca(),
                                p.getIdade())).toList();
    }


        public String cadastrarPet(String idOuNome, PetDTO petDto){

            Abrigo abrigo = getAbrigo(idOuNome);
            Pet pet = new Pet(petDto);
            pet.setAbrigo(abrigo);
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
            return pet.toString();
        }



    private Abrigo getAbrigo(String idOuNome) {
        if (Character.isDigit(idOuNome.charAt(0))) {
            Abrigo abrigo;
            try {
                abrigo = abrigoRepository.getReferenceById(Long.parseLong(idOuNome));
            } catch (EntityNotFoundException e) {
                throw new ValidacaoException("Abrigo não encontrado!");
            }

            return abrigo;
        }else {
            Optional<Abrigo> abrigoOptional = abrigoRepository.findByNome(idOuNome);
            if (abrigoOptional.isEmpty()) {
                throw new EntityNotFoundException("Abrigo não encontrado!");
            }
            return abrigoOptional.get();
        }
    }

}


