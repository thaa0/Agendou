package com.agendou.agendou_api.auth.service;

import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtApplicationServiceTest {

    @InjectMocks
    private JwtApplicationService jwtService;

    private void configurarJwtService() {
        jwtService.expiracao = "3600000"; // 1 hora
        jwtService.chave = "m7UoDqT1UjKg0mPVv8WrBpdFjM9iM0Jr9N9Z4VbVXZl4r7y3hwc5v1bOq";
    }

    private Usuario criarUsuario() {
        return new Usuario(new UsuarioRequest(
                "Usuário Teste",
                "nome",
                "123456",
                "teste@email.com",
                "11999999999"
        ), new BCryptPasswordEncoder());
    }

    @Test
    void testGerarToken_ComUsuario() {
        configurarJwtService();
        Usuario usuario = criarUsuario();

        String token = jwtService.gerarToken(usuario);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGerarToken_ComAuthentication() {
        configurarJwtService();
        Usuario usuario = criarUsuario();

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(usuario);

        String token = jwtService.gerarToken(auth);

        assertNotNull(token);
        assertTrue(token.length() > 0);
        verify(auth, times(1)).getPrincipal();
    }

    @Test
    void testGetUsuario_Valido() {
        configurarJwtService();
        Usuario usuario = criarUsuario();

        String token = jwtService.gerarToken(usuario);
        Optional<String> email = jwtService.getUsuario(token);

        assertTrue(email.isPresent());
        assertEquals("teste@email.com", email.get());
    }

    @Test
    void testGetUsuarioByBearerToken_Valido() {
        configurarJwtService();
        Usuario usuario = criarUsuario();

        String token = jwtService.gerarToken(usuario);
        String bearer = "Bearer " + token;

        Optional<String> email = jwtService.getUsuarioByBearerToken(bearer);

        assertTrue(email.isPresent());
        assertEquals("teste@email.com", email.get());
    }

    @Test
    void testGetUsuarioByBearerToken_Invalido() {
        Optional<String> result = jwtService.getUsuarioByBearerToken("TokenInvalido");
        assertTrue(result.isEmpty());

        result = jwtService.getUsuarioByBearerToken(null);
        assertTrue(result.isEmpty());
    }
}
