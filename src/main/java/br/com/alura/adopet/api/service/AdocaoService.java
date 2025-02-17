package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidadorAdocoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {
    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired private List<ValidadorAdocoes> validadores;

    EmailService emailService = new EmailService();


    public void solicitar(SolicitacaoAdocaoDTO dto){
        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validadores.forEach(v -> v.validar(dto));

        Adocao adocao = new Adocao(pet, tutor, dto.motivo());
        adocaoRepository.save(adocao);


        String emailTo = adocao.getPet().getAbrigo().getEmail();
        String subject = "Solicitação de adoção";
        String nomeDestinatario = adocao.getPet().getAbrigo().getNome();

        String message = "Olá " +nomeDestinatario +
                "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +
                ". \nFavor avaliar para aprovação ou reprovação.";

        emailService.enviarEmail(emailTo, subject, message);
    }

    public void aprovar(AprovacaoAdocaoDTO dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoAprovada();

        String emailTo = adocao.getTutor().getEmail();
        String subject = "Adoção aprovada";

        String message = "Parabéns " +adocao.getTutor().getNome() +
                "!\n\nSua adoção do pet " +adocao.getPet().getNome() +
                ", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +
                " para agendar a busca do seu pet.";

        emailService.enviarEmail(emailTo,subject,message);
    }

    public void reprovar(ReprovacaoAdocaoDTO dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoReprovada(dto.justificativa());

        String emailTo = adocao.getTutor().getEmail();
        String subject = "Adoção reprovada";
        String message = "Olá " +adocao.getTutor().getNome() +
                "!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +
                ", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: "+
                adocao.getJustificativaStatus();
        emailService.enviarEmail(emailTo,subject,message);
    }
}
