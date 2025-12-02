package com.agendou.agendou_api.usuario.application.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String senha;
}