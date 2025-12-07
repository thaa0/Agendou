package com.agendou.agendou_api.profissional.application.controller.dto;

import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class ProfissionalResponse {
    public UUID id;
    public UsuarioResponse usuario;
    public String descricao;
    public ConfiguracaoProfissionalResponse configuracao;
    public List<AgendaPadraoResponse> horarios;

    public ProfissionalResponse(Profissional profissional) {
        this.id = profissional.getId();
        this.usuario = new UsuarioResponse(profissional.getUsuario());
        this.descricao = profissional.getDescricao();
        this.configuracao = new ConfiguracaoProfissionalResponse(profissional.getConfiguracao());
        this.horarios = profissional.getHorarios() != null
            ? profissional.getHorarios().stream()
                .map(AgendaPadraoResponse::new)
                .collect(Collectors.toList())
            : Collections.emptyList();
    }
}