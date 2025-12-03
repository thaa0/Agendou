package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.domain.Profissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfissionalInfraRepositoryTest {

    private ProfissionalSpringDataJpaRepository springRepository;
    private ProfissionalInfraRepository repository;

    @BeforeEach
    void setup() {
        springRepository = Mockito.mock(ProfissionalSpringDataJpaRepository.class);
        repository = new ProfissionalInfraRepository(springRepository);
    }

    @Test
    void dadoProfissionalValido_quandoSalvar_entaoRetornaProfissionalSalvo() {
        Profissional profissional = new Profissional();
        when(springRepository.save(profissional)).thenReturn(profissional);

        Profissional result = repository.salva(profissional);

        assertNotNull(result);
        assertEquals(profissional, result);
        verify(springRepository, times(1)).save(profissional);
    }


    @Test
    void dadoIdExistente_quandoBuscar_entaoRetornaProfissional() {
        UUID id = UUID.randomUUID();
        Profissional profissional = new Profissional();

        when(springRepository.findByUsuarioId(id))
                .thenReturn(Optional.of(profissional));

        Profissional result = repository.buscaPorIdUsuario(id);

        assertNotNull(result);
        assertEquals(profissional, result);
        verify(springRepository, times(1)).findByUsuarioId(id);
    }


    @Test
    void dadoIdInexistente_quandoBuscar_entaoLancaAPIExceptionNotFound() {
        UUID id = UUID.randomUUID();

        when(springRepository.findByUsuarioId(id))
                .thenReturn(Optional.empty());

        APIException exception = assertThrows(APIException.class,
                () -> repository.buscaPorIdUsuario(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusException());
        assertTrue(exception.getMessage().contains("Profissional não encontrada"));
        verify(springRepository, times(1)).findByUsuarioId(id);
    }
}
