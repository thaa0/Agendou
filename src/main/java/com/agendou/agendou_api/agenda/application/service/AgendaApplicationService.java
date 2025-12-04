package com.agendou.agendou_api.agenda.application.service;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.agenda.application.repository.AgendaRepository;
import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AgendaApplicationService implements AgendaService {
    private final AgendaRepository agendaRepository;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public void configuraAgenda(List<AgendaPadraoRequest> agendas, Usuario user) {
        log.info("[start] AgendaApplicationService - configuraAgenda de user com id {}", user.getId());
        var profissional = profissionalRepository.buscaPorIdUsuario(user.getId());
        Map<Integer, AgendaPadrao> agendasExistentes = getAgendaPadraoMap(profissional);
        atualizaAgendaPadrao(agendas, agendasExistentes, profissional);
        log.debug("[finish] AgendaApplicationService - configuraAgenda");
    }

    private void atualizaAgendaPadrao(List<AgendaPadraoRequest> agendas, Map<Integer, AgendaPadrao> agendasExistentes, Profissional profissional) {
        log.info("[start] AgendaApplicationService - atualizaAgendaPadrao");
        Map<Integer, AgendaPadraoRequest> agendasPorDia = agendas.stream()
                .collect(Collectors.toMap(
                        AgendaPadraoRequest::getDiaSemana,
                        Function.identity(),
                        (r1, r2) -> r2
                ));

        for (var entry : agendasPorDia.entrySet()) {
            int diaSemana = entry.getKey();
            AgendaPadraoRequest req = entry.getValue();
            AgendaPadrao agenda = agendasExistentes.getOrDefault(diaSemana, new AgendaPadrao());
            agenda.atualiza(req, profissional);
            agendaRepository.salvar(agenda);
        }

        log.debug("[finish] AgendaApplicationService - atualizaAgendaPadrao");
    }

    private Map<Integer, AgendaPadrao> getAgendaPadraoMap(Profissional profissional) {
        log.info("[start] AgendaApplicationService - getAgendaPadraoMap");
        return Optional.ofNullable(agendaRepository.buscarPorProfissionalId(profissional.getId()))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(AgendaPadrao::getDiaSemana, Function.identity()));
    }

}
