package com.agendou.agendou_api.usuario.infra;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioInfraRepositoryTest {

    @Mock
    private UsuarioSpringDataJpaRepository usuarioSpringDataRepository;

    @InjectMocks
    private UsuarioInfraRepository usuarioInfraRepository;

    @Test
    void testSalva_Success() {
        Usuario usuario = new Usuario();
        usuarioInfraRepository.salva(usuario);
        verify(usuarioSpringDataRepository, times(1)).save(usuario);
    }

    @Test
    void testBuscaUsuario_Success() {
        String email = "teste@email.com";
        UsuarioRequest request = new UsuarioRequest(
                "Usuário Teste",
                "nome",
                "123456",
                "teste@email.com",
                "11999999999"
        );
        Usuario usuario = new Usuario(request,new BCryptPasswordEncoder());

        when(usuarioSpringDataRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioInfraRepository.buscaUsuario(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(usuarioSpringDataRepository, times(1)).findByEmail(email);
    }

    @Test
    void testBuscaUsuario_NotFound() {
        String email = "inexistente@email.com";

        when(usuarioSpringDataRepository.findByEmail(email)).thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class, () ->
                usuarioInfraRepository.buscaUsuario(email)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Usuário não encontrado!", exception.getMessage());
        verify(usuarioSpringDataRepository, times(1)).findByEmail(email);
    }
}