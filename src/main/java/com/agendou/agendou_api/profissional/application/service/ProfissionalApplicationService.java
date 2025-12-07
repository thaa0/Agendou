package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalRequest;
import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalResponse;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
}
