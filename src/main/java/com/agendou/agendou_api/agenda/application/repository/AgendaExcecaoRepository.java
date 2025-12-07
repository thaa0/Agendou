package com.agendou.agendou_api.agenda.application.repository;

import com.agendou.agendou_api.agenda.domain.AgendaExcecao;

import java.util.List;
import java.util.UUID;

public interface AgendaExcecaoRepository {
    AgendaExcecao salvar(AgendaExcecao excecao);
    List<AgendaExcecao> buscarPorProfissionalId(UUID profissionalId);
    AgendaExcecao buscarPorId(UUID id);
    void deletar(AgendaExcecao excecao);
    void validarPropriedade(AgendaExcecao excecao, UUID profissionalId);
    void validarDuplicidade(AgendaExcecao excecao);
}

