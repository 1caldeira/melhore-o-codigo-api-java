package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidadorAdocoes {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDTO dto){
        List<Adocao> adocoesTutorDoMesmoId = adocaoRepository.findAllById(dto.idTutor());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        if(adocoesTutorDoMesmoId.size() > 0){
            for (Adocao adocao:adocoesTutorDoMesmoId) {
                if(adocao.getTutor() == tutor && adocao.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO){
                    throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
                }
            }
        }
    }
}
