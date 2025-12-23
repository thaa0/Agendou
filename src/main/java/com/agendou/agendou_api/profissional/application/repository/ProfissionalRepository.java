package com.agendou.agendou_api.profissional.application.repository;

import com.agendou.agendou_api.profissional.domain.Profissional;

import java.util.UUID;

public interface ProfissionalRepository {
    Profissional salva(Profissional profissional);
    Profissional buscaPorIdUsuario(UUID id);
    boolean existeSlug(String slug);
}
