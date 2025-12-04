package com.agendou.agendou_api.profissional.application.controller.dto;

import jakarta.validation.constraints.*;

public record ConfiguracaoProfissionalRequest (

        @Min(value = 1, message = "O intervalo de cancelamento deve ser de pelo menos 1 hora.")
        @Max(value = 72, message = "O intervalo de cancelamento não pode exceder 72 horas.")
        int intervaloCancelamentoHoras,

        @NotBlank
        @Size(max = 500, message = "A mensagem de lembrete deve ter no máximo 500 caracteres.")
        String msgLembreteAtendimento,

        @NotBlank
        @Size(max = 500, message = "A mensagem pós-atendimento deve ter no máximo 500 caracteres.")
        String msgPosAtendimento
) { }
