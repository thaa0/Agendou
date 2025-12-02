package com.agendou.agendou_api.auth.service;

import com.agendou.agendou_api.auth.domain.Token;
import com.agendou.agendou_api.usuario.application.controller.dto.LoginRequest;
import jakarta.validation.Valid;

public interface AuthService {
    Token login(@Valid LoginRequest request);
}
