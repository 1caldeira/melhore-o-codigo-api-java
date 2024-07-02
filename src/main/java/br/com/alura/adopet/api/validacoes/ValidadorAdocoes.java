package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;

public interface ValidadorAdocoes {

    void validar(SolicitacaoAdocaoDTO dto);
}
