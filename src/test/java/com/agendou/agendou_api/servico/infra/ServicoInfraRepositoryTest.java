package com.agendou.agendou_api.servico.infra;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.servico.domain.Servico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoInfraRepositoryTest {

    private ServicoSpringDataJpaRepository springRepository;
    private ServicoInfraRepository repository;

    @BeforeEach
    void setup() {
        springRepository = Mockito.mock(ServicoSpringDataJpaRepository.class);
        repository = new ServicoInfraRepository(springRepository);
    }

    @Test
    void dadoServicoValido_quandoSalvar_entaoRetornaServicoSalvo() {
        // Arrange
        Servico servico = new Servico();
        when(springRepository.save(servico)).thenReturn(servico);

        // Act
        Servico result = repository.salva(servico);

        // Assert
        assertNotNull(result);
        assertEquals(servico, result);
        verify(springRepository, times(1)).save(servico);
    }

    @Test
    void dadoIdExistente_quandoBuscar_entaoRetornaServico() {
        // Arrange
        UUID id = UUID.randomUUID();
        Servico servico = new Servico();

        when(springRepository.findById(id))
                .thenReturn(Optional.of(servico));

        // Act
        Servico result = repository.buscaPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(servico, result);
        verify(springRepository, times(1)).findById(id);
    }

    @Test
    void dadoIdInexistente_quandoBuscar_entaoLancaAPIExceptionNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(springRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        APIException exception = assertThrows(APIException.class,
                () -> repository.buscaPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertTrue(exception.getMessage().contains("Serviço não encontrado"));
        verify(springRepository, times(1)).findById(id);
    }

    @Test
    void dadoProfissionalId_quandoBuscarServicos_entaoRetornaListaDeServicos() {
        // Arrange
        UUID profissionalId = UUID.randomUUID();
        Servico servico1 = new Servico();
        Servico servico2 = new Servico();
        List<Servico> servicos = Arrays.asList(servico1, servico2);

        when(springRepository.findByProfissionalId(profissionalId))
                .thenReturn(servicos);

        // Act
        List<Servico> result = repository.buscaPorProfissionalId(profissionalId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(servicos, result);
        verify(springRepository, times(1)).findByProfissionalId(profissionalId);
    }

    @Test
    void dadoProfissionalSemServicos_quandoBuscar_entaoRetornaListaVazia() {
        // Arrange
        UUID profissionalId = UUID.randomUUID();

        when(springRepository.findByProfissionalId(profissionalId))
                .thenReturn(Arrays.asList());

        // Act
        List<Servico> result = repository.buscaPorProfissionalId(profissionalId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(springRepository, times(1)).findByProfissionalId(profissionalId);
    }

    @Test
    void dadoServicoValido_quandoDeletar_entaoExecutaDeletar() {
        // Arrange
        Servico servico = new Servico();
        doNothing().when(springRepository).delete(servico);

        // Act
        repository.deleta(servico);

        // Assert
        verify(springRepository, times(1)).delete(servico);
    }

    @Test
    void dadoNulo_quandoSalvar_entaoLancaExcecao() {
        // Arrange
        when(springRepository.save(null))
                .thenThrow(new IllegalArgumentException("Servico não pode ser nulo"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> repository.salva(null));
    }
}

