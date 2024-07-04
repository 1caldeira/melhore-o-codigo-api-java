package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesAbrigo;
import br.com.alura.adopet.api.dto.SolicitacaoCadastroAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbrigoService {
    @Autowired
    private AbrigoRepository abrigoRepository;

    public void cadastrarAbrigo(SolicitacaoCadastroAbrigoDTO dto) {

        boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            Abrigo abrigo = new Abrigo(dto);
            abrigoRepository.save(abrigo);
        }
    }

    public List<DadosDetalhesAbrigo> listar() {
        List<Abrigo> abrigos = abrigoRepository.findAll();

        if (!abrigos.isEmpty() && abrigos != null) {
            List<DadosDetalhesAbrigo> dadosDetalhesAbrigoList = new ArrayList<>();
            for (Abrigo abrigo : abrigos) {
                DadosDetalhesAbrigo dadosDetalhesAbrigo = new DadosDetalhesAbrigo(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
                dadosDetalhesAbrigoList.add(dadosDetalhesAbrigo);
            }
            return dadosDetalhesAbrigoList;
        } else {
            throw new ValidacaoException("Nao ha abrigos para listar!");
        }
    }
}
