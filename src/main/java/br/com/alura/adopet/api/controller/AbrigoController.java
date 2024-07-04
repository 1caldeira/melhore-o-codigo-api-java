package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesAbrigo;
import br.com.alura.adopet.api.dto.SolicitacaoCadastroAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesAbrigo>> listar() {
        List<DadosDetalhesAbrigo> response = abrigoService.listar();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastroAbrigoDTO dto) {
        try {
            abrigoService.cadastrarAbrigo(dto);
            return ResponseEntity.ok().build();
        }catch(ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
