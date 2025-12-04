package com.agendou.agendou_api.agenda.application.controller.dto;

import com.agendou.agendou_api.profissional.domain.Profissional;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
public class AgendaPadraoRequest {
    @Min(1)
    @Max(7)
    private int diaSemana;

    @NotBlank
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Formato da hora deve ser HH:mm")
    private String horaInicio;

    @NotBlank
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Formato da hora deve ser HH:mm")
    private String horaFim;

}
