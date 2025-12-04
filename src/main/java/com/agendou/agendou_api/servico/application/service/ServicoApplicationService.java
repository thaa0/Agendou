package com.agendou.agendou_api.servico.application.service;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoResponse;
import com.agendou.agendou_api.servico.application.repository.ServicoRepository;
import com.agendou.agendou_api.servico.domain.Servico;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ServicoApplicationService implements ServicoService {
    private final ServicoRepository servicoRepository;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public ServicoResponse cadastra(UUID usuarioId, ServicoRequest servicoRequest) {
        log.info("[start] ServicoApplicationService - cadastra");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        Servico servico = Servico.builder()
                .profissional(profissional)
                .nome(servicoRequest.nome())
                .descricao(servicoRequest.descricao())
                .duracaoMin(servicoRequest.duracaoMin())
                .build();
        servicoRepository.salva(servico);
        log.debug("[finish] ServicoApplicationService - cadastra");
        return new ServicoResponse(servico);
    }

    @Override
    public ServicoResponse atualiza(UUID usuarioId, UUID servicoId, ServicoRequest servicoRequest) {
        log.info("[start] ServicoApplicationService - atualiza");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        Servico servico = servicoRepository.buscaPorId(servicoId);
        validaProfissionalDoServico(profissional, servico);
        servico.atualiza(servicoRequest);
        servicoRepository.salva(servico);
        log.debug("[finish] ServicoApplicationService - atualiza");
        return new ServicoResponse(servico);
    }

    @Override
    public void deleta(UUID usuarioId, UUID servicoId) {
        log.info("[start] ServicoApplicationService - deleta");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        Servico servico = servicoRepository.buscaPorId(servicoId);
        validaProfissionalDoServico(profissional, servico);
        servicoRepository.deleta(servico);
        log.debug("[finish] ServicoApplicationService - deleta");
    }

    @Override
    public List<ServicoResponse> listaPorProfissional(UUID usuarioId) {
        log.info("[start] ServicoApplicationService - listaPorProfissional");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        List<Servico> servicos = servicoRepository.buscaPorProfissionalId(profissional.getId());
        log.debug("[finish] ServicoApplicationService - listaPorProfissional");
        return ServicoResponse.converte(servicos);
    }

    @Override
    public ServicoResponse buscaPorId(UUID usuarioId, UUID servicoId) {
        log.info("[start] ServicoApplicationService - buscaPorId");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        Servico servico = servicoRepository.buscaPorId(servicoId);
        validaProfissionalDoServico(profissional, servico);
        log.debug("[finish] ServicoApplicationService - buscaPorId");
        return new ServicoResponse(servico);
    }

    private void validaProfissionalDoServico(Profissional profissional, Servico servico) {
        if (!servico.getProfissional().getId().equals(profissional.getId())) {
            throw APIException.build(HttpStatus.FORBIDDEN, "Serviço não pertence ao profissional");
        }
    }
}

