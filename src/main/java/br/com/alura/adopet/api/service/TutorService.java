package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAtualizacaoTutorDTO;
import br.com.alura.adopet.api.dto.SolicitacaoCadastroTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoTutorJaCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ValidacaoTutorJaCadastrado validacaoTutorJaCadastrado;
    public String cadastrar(SolicitacaoCadastroTutorDTO solicitacaoCadastroTutorDTO) {
        validacaoTutorJaCadastrado.validar(solicitacaoCadastroTutorDTO);
        Tutor tutor = new Tutor(solicitacaoCadastroTutorDTO);
        tutorRepository.save(tutor);
        return tutor.toString();
    }

    public String atualizar(SolicitacaoAtualizacaoTutorDTO dto) {

        Tutor tutor = tutorRepository.getReferenceById(dto.id());
        tutor.atualizar(dto);
        tutorRepository.save(tutor);
        return tutor.toString();
    }
}
