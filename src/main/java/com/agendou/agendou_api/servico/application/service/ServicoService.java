package com.agendou.agendou_api.servico.application.service;

import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoResponse;

import java.util.List;
import java.util.UUID;

public interface ServicoService {
    ServicoResponse cadastra(UUID usuarioId, ServicoRequest servicoRequest);
    ServicoResponse atualiza(UUID usuarioId, UUID servicoId, ServicoRequest servicoRequest);
    void deleta(UUID usuarioId, UUID servicoId);
    List<ServicoResponse> listaPorProfissional(UUID usuarioId);
    ServicoResponse buscaPorId(UUID usuarioId, UUID servicoId);
}

