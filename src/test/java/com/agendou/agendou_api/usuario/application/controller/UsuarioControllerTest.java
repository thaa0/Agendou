package com.agendou.agendou_api.usuario.application.controller;

import com.agendou.agendou_api.auth.domain.Token;
import com.agendou.agendou_api.auth.service.AuthService;
import com.agendou.agendou_api.usuario.application.controller.dto.LoginRequest;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.application.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private AuthService authService;
    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void testPostNovoUsuario_Success() {
        UsuarioRequest usuarioNovo = mock(UsuarioRequest.class);

        assertDoesNotThrow(() -> usuarioController.postNovoUsuario(usuarioNovo));

        verify(usuarioService, times(1)).cadastrarUsuario(usuarioNovo);
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("email", "1234");
        Token token = new Token("bearer","meu-token", UUID.randomUUID());
        when(authService.login(loginRequest)).thenReturn(token);

        Token response = usuarioController.login(loginRequest);

        assertNotNull(response);
        assertEquals("meu-token", response.getToken());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testLogin_Failure() {
        LoginRequest loginRequest = new LoginRequest("email", "1234");
        when(authService.login(loginRequest))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioController.login(loginRequest));

        assertEquals(HttpStatusCode.valueOf(401), exception.getStatusCode());
        assertEquals("Credenciais inválidas", exception.getReason());
    }
}