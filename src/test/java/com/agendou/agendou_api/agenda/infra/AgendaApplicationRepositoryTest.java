package com.agendou.agendou_api.agenda.infra;

import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaApplicationRepositoryTest {

    @Mock
    private AgendaSpringDataJpaRepository springDataJpaRepository;

    @InjectMocks
    private AgendaApplicationRepository repository;

    private UUID profissionalId;

    @BeforeEach
    void setup() {
        profissionalId = UUID.randomUUID();
    }

    @Test
    void deveRetornarListaDeAgendasPorProfissionalId() {
        AgendaPadrao agenda1 = new AgendaPadrao();
        AgendaPadrao agenda2 = new AgendaPadrao();

        when(springDataJpaRepository.findAllByProfissionalId(profissionalId))
                .thenReturn(List.of(agenda1, agenda2));

        List<AgendaPadrao> resultado = repository.buscarPorProfissionalId(profissionalId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(springDataJpaRepository, times(1)).findAllByProfissionalId(profissionalId);
    }

    @Test
    void deveSalvarAgendaChamandoSpringDataJpa() {
        AgendaPadrao agenda = new AgendaPadrao();

        repository.salvar(agenda);

        verify(springDataJpaRepository, times(1)).save(agenda);
    }
}
