package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;

import java.util.UUID;

public interface ConfiguracaoService {
    void configura(UUID id, ConfiguracaoProfissionalRequest configuracaoProfissionalRequest);
}
