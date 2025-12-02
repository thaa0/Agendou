package com.agendou.agendou_api.auth.config.service;

import com.agendou.agendou_api.auth.domain.Token;
import com.agendou.agendou_api.usuario.application.controller.dto.LoginRequest;
import com.agendou.agendou_api.usuario.application.repository.UsuarioRepository;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthApplicationService implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Override
    public Token login(LoginRequest request) {
        log.info("[start] AuthApplicationService - login");
        autentica(request);
        Usuario usuario = usuarioRepository.buscaUsuario(request.getEmail());
        String token = jwtService.gerarToken(usuario);
        log.debug("[finish] AuthApplicationService - login");
        return new Token("Bearer", token, usuario.getId());
    }

    private void autentica(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha());
        authenticationManager.authenticate(authToken);
    }
}
