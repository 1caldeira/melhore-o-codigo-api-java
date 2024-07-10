package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.service.PetService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/{idOuNome}/pets/disponiveis")
    public ResponseEntity<String> listarTodosDisponiveis() {
        List<DadosDetalhesPet> disponiveis = petService.listarTodosNaoAdotados();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return ResponseEntity.ok(gson.toJson(disponiveis));
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<String> listarPets(@PathVariable String idOuNome) {

        List<DadosDetalhesPet> pets = petService.listarPorAbrigo(idOuNome);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return ResponseEntity.ok(gson.toJson(pets));
        }


    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid PetDTO petDto) {
        String response = petService.cadastrarPet(idOuNome, petDto);
        return ResponseEntity.ok(response);
        }

    }

