package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoCadastroTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorJaCadastrado {

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoCadastroTutorDTO solicitacaoCadastroTutorDTO) {
        boolean telefoneJaCadastrado = tutorRepository.existsByTelefone(solicitacaoCadastroTutorDTO.telefone());
        boolean emailJaCadastrado = tutorRepository.existsByEmail(solicitacaoCadastroTutorDTO.email());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }
    }
}
