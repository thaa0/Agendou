package com.agendou.agendou_api.profissional.application.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfissionalRequest (@NotBlank(message = "Descricao não deve estar em branco") String descricao){ }