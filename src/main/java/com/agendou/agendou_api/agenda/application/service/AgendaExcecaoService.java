package com.agendou.agendou_api.agenda.application.service;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoRequest;
import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoResponse;

import java.util.List;
import java.util.UUID;

public interface AgendaExcecaoService {
    AgendaExcecaoResponse criarExcecao(UUID usuarioId, AgendaExcecaoRequest request);
    List<AgendaExcecaoResponse> listarExcecoes(UUID usuarioId);
    void deletarExcecao(UUID usuarioId, UUID excecaoId);
}

