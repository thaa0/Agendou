package com.agendou.agendou_api.servico.application.controller;

import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoResponse;
import com.agendou.agendou_api.servico.application.service.ServicoService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoControllerTest {

    @Mock
    private ServicoService servicoService;

    @InjectMocks
    private ServicoController controller;

    private Usuario usuario;
    private UUID usuarioId;
    private UUID servicoId;
    private ServicoRequest servicoRequest;
    private ServicoResponse servicoResponse;

    @BeforeEach
    void setup() {
        usuarioId = UUID.randomUUID();
        servicoId = UUID.randomUUID();

        usuario = mock(Usuario.class);
        when(usuario.getId()).thenReturn(usuarioId);

        servicoRequest = new ServicoRequest(
                "Corte de Cabelo",
                "Corte masculino tradicional",
                30
        );

        servicoResponse = new ServicoResponse(
                servicoId,
                "Corte de Cabelo",
                "Corte masculino tradicional",
                30
        );
    }

    @Test
    void deveCadastrarServicoComSucesso() {
        // Arrange
        when(servicoService.cadastra(usuarioId, servicoRequest))
                .thenReturn(servicoResponse);

        // Act
        ServicoResponse response = controller.cadastra(servicoRequest, usuario);

        // Assert
        assertNotNull(response);
        assertEquals(servicoId, response.getId());
        assertEquals("Corte de Cabelo", response.getNome());
        verify(servicoService, times(1)).cadastra(usuarioId, servicoRequest);
    }

    @Test
    void deveAtualizarServicoComSucesso() {
        // Arrange
        when(servicoService.atualiza(usuarioId, servicoId, servicoRequest))
                .thenReturn(servicoResponse);

        // Act
        ServicoResponse response = controller.atualiza(servicoId, servicoRequest, usuario);

        // Assert
        assertNotNull(response);
        assertEquals(servicoId, response.getId());
        verify(servicoService, times(1)).atualiza(usuarioId, servicoId, servicoRequest);
    }

    @Test
    void deveDeletarServicoComSucesso() {
        // Arrange
        doNothing().when(servicoService).deleta(usuarioId, servicoId);

        // Act
        controller.deleta(servicoId, usuario);

        // Assert
        verify(servicoService, times(1)).deleta(usuarioId, servicoId);
    }

    @Test
    void deveListarServicosPorProfissionalComSucesso() {
        // Arrange
        ServicoResponse servico2 = new ServicoResponse(
                UUID.randomUUID(),
                "Barba",
                "Aparar barba",
                15
        );

        List<ServicoResponse> servicos = Arrays.asList(servicoResponse, servico2);

        when(servicoService.listaPorProfissional(usuarioId))
                .thenReturn(servicos);

        // Act
        List<ServicoResponse> response = controller.listaPorProfissional(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(servicoService, times(1)).listaPorProfissional(usuarioId);
    }

    @Test
    void deveBuscarServicoPorIdComSucesso() {
        // Arrange
        when(servicoService.buscaPorId(usuarioId, servicoId))
                .thenReturn(servicoResponse);

        // Act
        ServicoResponse response = controller.buscaPorId(servicoId, usuario);

        // Assert
        assertNotNull(response);
        assertEquals(servicoId, response.getId());
        assertEquals("Corte de Cabelo", response.getNome());
        verify(servicoService, times(1)).buscaPorId(usuarioId, servicoId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverServicos() {
        // Arrange
        when(servicoService.listaPorProfissional(usuarioId))
                .thenReturn(Arrays.asList());

        // Act
        List<ServicoResponse> response = controller.listaPorProfissional(usuario);

        // Assert
        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(servicoService, times(1)).listaPorProfissional(usuarioId);
    }
}

