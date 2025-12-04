package com.agendou.agendou_api.servico.application.controller.dto;

import com.agendou.agendou_api.servico.domain.Servico;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor
public class ServicoResponse {
    UUID id;
    String nome;
    String descricao;
    Integer duracaoMin;

    public ServicoResponse(Servico servico) {
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.descricao = servico.getDescricao();
        this.duracaoMin = servico.getDuracaoMin();
    }

    public static List<ServicoResponse> converte(List<Servico> servicos) {
        return servicos.stream()
                .map(ServicoResponse::new)
                .toList();
    }
}

