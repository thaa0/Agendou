package com.agendou.agendou_api.usuario.application.service;


import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;

public interface UsuarioService {
    void cadastrarUsuario(UsuarioRequest request);
}
