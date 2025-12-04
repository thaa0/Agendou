package com.agendou.agendou_api.agenda.infra;

import com.agendou.agendou_api.agenda.application.repository.AgendaRepository;
import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class AgendaApplicationRepository implements AgendaRepository {
    private final AgendaSpringDataJpaRepository agendaSpringDataJpaRepository;

    @Override
    public List<AgendaPadrao> buscarPorProfissionalId(UUID id) {
        log.info("[start] AgendaApplicationRepository - buscarPorProfissionalId - {}", id);
        return agendaSpringDataJpaRepository.findAllByProfissionalId(id);
    }

    @Override
    public void salvar(AgendaPadrao agenda) {
        log.info("[start] AgendaApplicationRepository - salvar");
        agendaSpringDataJpaRepository.save(agenda);
    }
}
