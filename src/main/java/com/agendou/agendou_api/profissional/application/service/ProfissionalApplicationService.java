package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalRequest;
import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalResponse;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProfissionalApplicationService implements ProfissionalService {
    private final ProfissionalRepository profissionalRepository;

    @Override
    public void finalizaCadastro(UUID id, ProfissionalRequest profissionalRequest) {
        log.info("[start] ProfissionalApplicationService - finalizaCadastro");
        Profissional profissional = buscaProfissional(id);
        profissional.setDescricao(profissionalRequest.descricao());
        profissional.setSlug(geraSlug());
        profissionalRepository.salva(profissional);
        log.debug("[finish] ProfissionalApplicationService - finalizaCadastro");
    }

    @Override
    public ProfissionalResponse obterProfissional(UUID id) {
        log.info("[start] ProfissionalApplicationService - obterProfissional {}", id);
        Profissional profissional = buscaProfissional(id);
        ProfissionalResponse profissionalResponse = new ProfissionalResponse(profissional);
        log.debug("[finish] ProfissionalApplicationService - obterProfissional");
        return profissionalResponse;
    }

    private Profissional buscaProfissional(UUID id) {
        log.info("[start] ProfissionalApplicationService - buscaProfissional com id: {}", id);
        return profissionalRepository.buscaPorIdUsuario(id);
    }

    private String geraSlug() {
        log.info("[start] ProfissionalApplicationService - geraSlug");
        String slug;
        int tentativas = 0;
        final int maxTentativas = 10;

        do {
            slug = geraSlugUnico();
            tentativas++;
            slug = validaTentativas(tentativas, maxTentativas, slug);
        } while (profissionalRepository.existeSlug(slug));

        log.info("[finish] ProfissionalApplicationService - geraSlug: {}", slug);
        return slug;
    }

    private static String validaTentativas(int tentativas, int maxTentativas, String slug) {
        if (tentativas >= maxTentativas) {
            slug = String.format("%08d", new SecureRandom().nextInt(100000000));
            log.warn("Máximo de tentativas atingido. Usando slug numérico sequencial: {}", slug);
        }
        return slug;
    }

    private String geraSlugUnico() {
        final String caracteres = "abcdefghijklmnopqrstuvwxyz0123456789";
        final int tamanhoSlug = 8;
        final SecureRandom random = new SecureRandom();
        final StringBuilder slug = new StringBuilder(tamanhoSlug);

        for (int i = 0; i < tamanhoSlug; i++) {
            int index = random.nextInt(caracteres.length());
            slug.append(caracteres.charAt(index));
        }

        return slug.toString();
    }
}
