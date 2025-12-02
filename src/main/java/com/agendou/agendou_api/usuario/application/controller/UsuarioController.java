package com.agendou.agendou_api.usuario.application.controller;

import com.agendou.agendou_api.auth.config.service.AuthService;
import com.agendou.agendou_api.auth.domain.Token;
import com.agendou.agendou_api.usuario.application.controller.dto.LoginRequest;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.application.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
@Log4j2
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthService authService;

    @PostMapping("/cadastro")
    @ResponseStatus(code = HttpStatus.CREATED)
    void postNovoUsuario(@RequestBody @Valid UsuarioRequest usuarioNovo) {
        log.info("[start] UsuarioController - postNovoUsuario");
        usuarioService.cadastrarUsuario(usuarioNovo);
        log.debug("[finish] UsuarioController - postNovoUsuario");
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public Token login(@RequestBody @Valid LoginRequest request) {
        log.info("[start] UsuarioController - login");
        Token tokenResponse = authService.login(request);
        log.debug("[finish] UsuarioController - login");
        return tokenResponse;
    }
}
