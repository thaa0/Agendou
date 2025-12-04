package com.agendou.agendou_api.servico.application.repository;

import com.agendou.agendou_api.servico.domain.Servico;

import java.util.List;
import java.util.UUID;

public interface ServicoRepository {
    Servico salva(Servico servico);
    Servico buscaPorId(UUID id);
    List<Servico> buscaPorProfissionalId(UUID profissionalId);
    void deleta(Servico servico);
}

