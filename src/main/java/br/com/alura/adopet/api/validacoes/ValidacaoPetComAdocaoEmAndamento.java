package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidadorAdocoes {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDTO dto){
        List<Adocao> adocoesPetsDoMesmoId = adocaoRepository.findAllById(dto.idPet());
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if(adocoesPetsDoMesmoId.size() > 0){
            for (Adocao adocao:adocoesPetsDoMesmoId) {
                if(adocao.getPet() == pet && adocao.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO){
                    throw new ValidacaoException("Pet já está aguardando adocao em andamento");
                }
            }
        }
    }
}
