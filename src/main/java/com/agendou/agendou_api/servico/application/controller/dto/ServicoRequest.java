package com.agendou.agendou_api.servico.application.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServicoRequest(
        @NotBlank(message = "Nome não deve estar em branco")
        String nome,
        String descricao,
        @NotNull(message = "Duração não deve ser nula")
        @Positive(message = "Duração deve ser maior que zero")
        Integer duracaoMin
) {
}

