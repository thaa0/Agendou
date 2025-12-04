package com.agendou.agendou_api.agenda.application.repository;

import com.agendou.agendou_api.agenda.domain.AgendaPadrao;

import java.util.List;
import java.util.UUID;

public interface AgendaRepository {
    List<AgendaPadrao> buscarPorProfissionalId(UUID id);
    void salvar(AgendaPadrao agenda);
}
