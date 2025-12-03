package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalRequest;

import java.util.UUID;

public interface ProfissionalService {
    void finalizaCadastro(UUID id, ProfissionalRequest profissionalRequest);
}
