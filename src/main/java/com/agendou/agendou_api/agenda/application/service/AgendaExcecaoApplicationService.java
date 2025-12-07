package com.agendou.agendou_api.agenda.application.service;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoRequest;
import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoResponse;
import com.agendou.agendou_api.agenda.application.repository.AgendaExcecaoRepository;
import com.agendou.agendou_api.agenda.domain.AgendaExcecao;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AgendaExcecaoApplicationService implements AgendaExcecaoService {
    private final AgendaExcecaoRepository agendaExcecaoRepository;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public AgendaExcecaoResponse criarExcecao(UUID usuarioId, AgendaExcecaoRequest request) {
        log.info("[start] AgendaExcecaoApplicationService - criarExcecao - usuarioId: {}", usuarioId);
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);

        AgendaExcecao excecao = AgendaExcecao.criarExcecao(
                profissional,
                request.getDataExcecaoAsLocalDate(),
                request.getHoraInicio(),
                request.getHoraFim(),
                request.getDiaInteiroFechado()
        );

        agendaExcecaoRepository.validarDuplicidade(excecao);
        AgendaExcecao excecaoSalva = agendaExcecaoRepository.salvar(excecao);
        log.debug("[finish] AgendaExcecaoApplicationService - criarExcecao");
        return new AgendaExcecaoResponse(excecaoSalva);
    }

    @Override
    public List<AgendaExcecaoResponse> listarExcecoes(UUID usuarioId) {
        log.info("[start] AgendaExcecaoApplicationService - listarExcecoes - usuarioId: {}", usuarioId);
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);

        List<AgendaExcecao> excecoes = agendaExcecaoRepository.buscarPorProfissionalId(profissional.getId());

        log.debug("[finish] AgendaExcecaoApplicationService - listarExcecoes");
        return excecoes.stream()
                .map(AgendaExcecaoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarExcecao(UUID usuarioId, UUID excecaoId) {
        log.info("[start] AgendaExcecaoApplicationService - deletarExcecao - usuarioId: {}, excecaoId: {}",
                usuarioId, excecaoId);
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(usuarioId);
        AgendaExcecao excecao = agendaExcecaoRepository.buscarPorId(excecaoId);
        agendaExcecaoRepository.validarPropriedade(excecao, profissional.getId());
        agendaExcecaoRepository.deletar(excecao);
        log.debug("[finish] AgendaExcecaoApplicationService - deletarExcecao");
    }
}

