package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesAbrigo;
import br.com.alura.adopet.api.dto.SolicitacaoCadastroAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoAbrigoJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class AbrigoService {
    @Autowired
    private AbrigoRepository abrigoRepository;
    @Autowired
    private ValidacaoAbrigoJaCadastrado validacaoAbrigoJaCadastrado;

    public void cadastrarAbrigo(SolicitacaoCadastroAbrigoDTO dto) {

        validacaoAbrigoJaCadastrado.validar(dto);

        Abrigo abrigo = new Abrigo(dto);
        abrigoRepository.save(abrigo);
    }

    public List<DadosDetalhesAbrigo> listar() {
        List<Abrigo> abrigos = abrigoRepository.findAll();
        if (!abrigos.isEmpty()) {
            return abrigos.stream()
                    .map(DadosDetalhesAbrigo::new)
                    .toList();
        } else {
            throw new ValidacaoException("Nao ha abrigos para listar!");
        }
    }
}
