package com.agendou.agendou_api.auth.service;

import com.agendou.agendou_api.usuario.domain.Usuario;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface JwtService {
    String gerarToken(Usuario usuario);
    Optional<String> getUsuarioByBearerToken(String token);
    String gerarToken(Authentication authentication);
}
