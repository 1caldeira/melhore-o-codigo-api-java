package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetRepository repository;
    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {
        List<DadosDetalhesPet> disponiveis = petService.listarTodosNaoAdotados();
        return ResponseEntity.ok(disponiveis);
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DadosDetalhesPet>> listarPets(@PathVariable String idOuNome) {
            List<DadosDetalhesPet> pets = petService.listarPorAbrigo(idOuNome);
            return ResponseEntity.ok(pets);
        }


    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid PetDTO petDto) {
            String response = petService.cadastrarPet(idOuNome, petDto);
            return ResponseEntity.ok(response);
        }
    }

