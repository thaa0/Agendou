package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalRequest;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfissionalApplicationServiceTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ProfissionalApplicationService service;

    @Test
    void deveFinalizarCadastroComSucesso() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Profissional profissional = new Profissional();
        ProfissionalRequest request = new ProfissionalRequest("Descrição nova");

        when(profissionalRepository.buscaPorIdUsuario(userId))
                .thenReturn(profissional);

        // Act
        service.finalizaCadastro(userId, request);

        // Assert
        assertEquals("Descrição nova", profissional.getDescricao());
        verify(profissionalRepository, times(1)).salva(profissional);
    }

    @Test
    void deveLancarNotFoundQuandoProfissionalNaoExistir() {
        // Arrange
        UUID userId = UUID.randomUUID();
        ProfissionalRequest request = new ProfissionalRequest("qualquer");

        doThrow(APIException.build(HttpStatus.NOT_FOUND, "Profissional não encontrado"))
                .when(profissionalRepository).buscaPorIdUsuario(userId);

        // Act
        APIException exception = assertThrows(
                APIException.class,
                () -> service.finalizaCadastro(userId, request)
        );

        // Assert
        assertEquals("Profissional não encontrado", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertEquals("Profissional não encontrado", exception.getBodyException().getMessage());

        verify(profissionalRepository, never()).salva(any());
    }

    @Test
    void naoDeveSalvarQuandoErroLancadoAntes() {
        // Arrange
        UUID userId = UUID.randomUUID();
        ProfissionalRequest request = new ProfissionalRequest("irrelevante");

        when(profissionalRepository.buscaPorIdUsuario(userId))
                .thenThrow(APIException.build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado"));

        // Act
        assertThrows(APIException.class,
                () -> service.finalizaCadastro(userId, request));

        // Assert
        verify(profissionalRepository, never()).salva(any());
    }
}
