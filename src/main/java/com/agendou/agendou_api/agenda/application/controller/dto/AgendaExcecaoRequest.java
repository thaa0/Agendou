package com.agendou.agendou_api.agenda.application.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Builder
public class AgendaExcecaoRequest {

    @NotBlank(message = "Data de exceção é obrigatória")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Formato da data deve ser yyyy-MM-dd")
    private String dataExcecao;

    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Formato da hora deve ser HH:mm")
    private String horaInicio;

    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Formato da hora deve ser HH:mm")
    private String horaFim;

    @NotNull(message = "Indicador de dia inteiro fechado é obrigatório")
    private Boolean diaInteiroFechado;

    public LocalDate getDataExcecaoAsLocalDate() {
        return LocalDate.parse(this.dataExcecao);
    }
}

