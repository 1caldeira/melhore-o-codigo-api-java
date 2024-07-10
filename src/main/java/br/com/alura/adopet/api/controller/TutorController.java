package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAtualizacaoTutorDTO;
import br.com.alura.adopet.api.dto.SolicitacaoCadastroTutorDTO;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastroTutorDTO solicitacaoCadastroTutorDTO) {
        String response = tutorService.cadastrar(solicitacaoCadastroTutorDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid SolicitacaoAtualizacaoTutorDTO solicitacaoAtualizacaoTutorDTO) {
        String response = tutorService.atualizar(solicitacaoAtualizacaoTutorDTO);
        return ResponseEntity.ok(response);
    }

}
