package com.agendou.agendou_api.profissional.application.controller.dto;

import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;

public record ConfiguracaoProfissionalResponse(
        int intervaloCancelamentoHoras,
        String msgLembreteAtendimento,
        String msgPosAtendimento
) {
    public ConfiguracaoProfissionalResponse(ConfiguracaoProfissional configuracao) {
        this(
                configuracao != null ? configuracao.getIntervaloCancelamentoHoras() : 0,
                configuracao != null ? configuracao.getMsgLembreteAtendimento() : null,
                configuracao != null ? configuracao.getMsgPosAtendimento() : null
        );
    }
}
