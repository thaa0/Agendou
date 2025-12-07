package com.agendou.agendou_api.profissional.application.controller.dto;

import com.agendou.agendou_api.agenda.domain.AgendaPadrao;

import java.util.UUID;

public record AgendaPadraoResponse(
        UUID id,
        int diaSemana,
        String horaInicio,
        String horaFim
) {
    public AgendaPadraoResponse(AgendaPadrao horario) {
        this(
                horario.getId(),
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFim()
        );
    }
}
