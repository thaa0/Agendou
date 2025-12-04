package com.agendou.agendou_api.servico.application.service;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoResponse;
import com.agendou.agendou_api.servico.application.repository.ServicoRepository;
import com.agendou.agendou_api.servico.domain.Servico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicoApplicationServiceTest {

    private ServicoRepository servicoRepository;
    private ProfissionalRepository profissionalRepository;
    private ServicoApplicationService service;

    @BeforeEach
    void setup() {
        servicoRepository = Mockito.mock(ServicoRepository.class);
        profissionalRepository = Mockito.mock(ProfissionalRepository.class);
        service = new ServicoApplicationService(servicoRepository, profissionalRepository);
    }

    @Test
    void dadoServicoRequestValido_quandoCadastrar_entaoRetornaServicoResponse() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        ServicoRequest request = new ServicoRequest("Corte", "Corte de cabelo", 30);

        Profissional profissional = Profissional.builder().id(profissionalId).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.salva(any(Servico.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ServicoResponse response = service.cadastra(usuarioId, request);

        // Assert
        assertNotNull(response);
        verify(profissionalRepository, times(1)).buscaPorIdUsuario(usuarioId);
        verify(servicoRepository, times(1)).salva(any(Servico.class));
    }

    @Test
    void dadoServicoExistente_quandoAtualizar_entaoRetornaServicoAtualizado() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        ServicoRequest request = new ServicoRequest("Corte Premium", "Corte especial", 45);

        Profissional profissional = Profissional.builder().id(profissionalId).build();

        Servico servico = Servico.builder()
                .id(servicoId)
                .profissional(profissional)
                .build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);
        when(servicoRepository.salva(any(Servico.class))).thenReturn(servico);

        // Act
        ServicoResponse response = service.atualiza(usuarioId, servicoId, request);

        // Assert
        assertNotNull(response);
        verify(profissionalRepository, times(1)).buscaPorIdUsuario(usuarioId);
        verify(servicoRepository, times(1)).buscaPorId(servicoId);
        verify(servicoRepository, times(1)).salva(servico);
    }

    @Test
    void dadoProfissionalDiferente_quandoAtualizar_entaoLancaAPIExceptionForbidden() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        ServicoRequest request = new ServicoRequest("Corte", "Desc", 30);

        Profissional profissional = Profissional.builder().id(UUID.randomUUID()).build();
        Profissional outroProfissional = Profissional.builder().id(UUID.randomUUID()).build();

        Servico servico = Servico.builder().profissional(outroProfissional).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);

        // Act & Assert
        APIException exception = assertThrows(APIException.class,
                () -> service.atualiza(usuarioId, servicoId, request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusException());
        assertTrue(exception.getMessage().contains("Serviço não pertence ao profissional"));
    }

    @Test
    void dadoServicoExistente_quandoDeletar_entaoExecutaDeletar() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();

        Profissional profissional = Profissional.builder().id(profissionalId).build();

        Servico servico = Servico.builder().profissional(profissional).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);
        doNothing().when(servicoRepository).deleta(servico);

        // Act
        service.deleta(usuarioId, servicoId);

        // Assert
        verify(profissionalRepository, times(1)).buscaPorIdUsuario(usuarioId);
        verify(servicoRepository, times(1)).buscaPorId(servicoId);
        verify(servicoRepository, times(1)).deleta(servico);
    }

    @Test
    void dadoProfissionalDiferente_quandoDeletar_entaoLancaAPIExceptionForbidden() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();

        Profissional profissional = Profissional.builder().id(UUID.randomUUID()).build();
        Profissional outroProfissional = Profissional.builder().id(UUID.randomUUID()).build();

        Servico servico = Servico.builder().profissional(outroProfissional).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);

        // Act & Assert
        APIException exception = assertThrows(APIException.class,
                () -> service.deleta(usuarioId, servicoId));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusException());
    }

    @Test
    void dadoProfissionalComServicos_quandoListar_entaoRetornaListaDeServicos() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();

        Profissional profissional = Profissional.builder().id(profissionalId).build();

        Servico servico1 = Servico.builder().build();
        Servico servico2 = Servico.builder().build();
        List<Servico> servicos = Arrays.asList(servico1, servico2);

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorProfissionalId(profissionalId)).thenReturn(servicos);

        // Act
        List<ServicoResponse> response = service.listaPorProfissional(usuarioId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(profissionalRepository, times(1)).buscaPorIdUsuario(usuarioId);
        verify(servicoRepository, times(1)).buscaPorProfissionalId(profissionalId);
    }

    @Test
    void dadoServicoExistente_quandoBuscarPorId_entaoRetornaServicoResponse() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();

        Profissional profissional = Profissional.builder().id(profissionalId).build();

        Servico servico = Servico.builder().profissional(profissional).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);

        // Act
        ServicoResponse response = service.buscaPorId(usuarioId, servicoId);

        // Assert
        assertNotNull(response);
        verify(profissionalRepository, times(1)).buscaPorIdUsuario(usuarioId);
        verify(servicoRepository, times(1)).buscaPorId(servicoId);
    }

    @Test
    void dadoProfissionalDiferente_quandoBuscarPorId_entaoLancaAPIExceptionForbidden() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();

        Profissional profissional = Profissional.builder().id(UUID.randomUUID()).build();
        Profissional outroProfissional = Profissional.builder().id(UUID.randomUUID()).build();

        Servico servico = Servico.builder().profissional(outroProfissional).build();

        when(profissionalRepository.buscaPorIdUsuario(usuarioId)).thenReturn(profissional);
        when(servicoRepository.buscaPorId(servicoId)).thenReturn(servico);

        // Act & Assert
        APIException exception = assertThrows(APIException.class,
                () -> service.buscaPorId(usuarioId, servicoId));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusException());
        assertTrue(exception.getMessage().contains("Serviço não pertence ao profissional"));
    }
}
