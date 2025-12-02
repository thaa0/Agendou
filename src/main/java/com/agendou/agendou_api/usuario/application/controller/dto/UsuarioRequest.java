package com.agendou.agendou_api.usuario.application.controller.dto;

import lombok.Getter;

@Getter
public class UsuarioRequest {
    private String nomeCompleto;
    private String nomeFantasia;
    private String whatsapp;
    private String email;
    private String senha;
}
