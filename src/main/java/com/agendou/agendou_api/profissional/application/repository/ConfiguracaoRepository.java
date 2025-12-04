package com.agendou.agendou_api.profissional.application.repository;

import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;

import java.util.Optional;
import java.util.UUID;

public interface ConfiguracaoRepository {
    Optional<ConfiguracaoProfissional> buscarPorProfissionalId(UUID id);
    void salvar(ConfiguracaoProfissional config);
}
