package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoCadastroAbrigoDTO;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AbrigoService {
    @Autowired
    private AbrigoRepository abrigoRepository;

    public void cadastrarAbrigo(SolicitacaoCadastroAbrigoDTO dto){
        boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());
    }
}
