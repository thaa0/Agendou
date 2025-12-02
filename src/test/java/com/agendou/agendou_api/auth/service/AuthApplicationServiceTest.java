package com.agendou.agendou_api.auth.service;

import com.agendou.agendou_api.auth.domain.Token;
import com.agendou.agendou_api.usuario.application.controller.dto.LoginRequest;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.application.repository.UsuarioRepository;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthApplicationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthApplicationService authService;

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("teste@email.com", "123456");
        Usuario usuario = new Usuario(new UsuarioRequest(
                "Usuário Teste",
                "nome",
                "123456",
                "teste@email.com",
                "11999999999"
        ), new BCryptPasswordEncoder());
        when(usuarioRepository.buscaUsuario(request.getEmail())).thenReturn(usuario);
        when(jwtService.gerarToken(usuario)).thenReturn("token123");

        Token tokenResponse = authService.login(request);

        assertNotNull(tokenResponse);
        assertEquals("Bearer", tokenResponse.getTipo());
        assertEquals("token123", tokenResponse.getToken());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository, times(1)).buscaUsuario(request.getEmail());
        verify(jwtService, times(1)).gerarToken(usuario);
    }
}