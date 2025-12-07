package com.agendou.agendou_api.agenda.application.controller.dto;

import com.agendou.agendou_api.agenda.domain.AgendaExcecao;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Builder
public class AgendaExcecaoResponse {

    private UUID id;
    private LocalDate dataExcecao;
    private String horaInicio;
    private String horaFim;
    private Boolean diaInteiroFechado;

    public AgendaExcecaoResponse(AgendaExcecao excecao) {
        this.id = excecao.getId();
        this.dataExcecao = excecao.getDataExcecao();
        this.horaInicio = excecao.getHoraInicio();
        this.horaFim = excecao.getHoraFim();
        this.diaInteiroFechado = excecao.getDiaInteiroFechado();
    }
}

