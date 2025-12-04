package com.agendou.agendou_api.agenda.application.controller;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.agenda.application.service.AgendaService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class AgendaControllerTest {

    @Mock
    private AgendaService agendaService;

    @InjectMocks
    private AgendaController agendaController;

    private Usuario usuario;
    private List<AgendaPadraoRequest> agendas;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .build();

        AgendaPadraoRequest agenda1 = AgendaPadraoRequest.builder()
                .diaSemana(1)
                .horaInicio("08:00")
                .horaFim("12:00")
                .build();

        AgendaPadraoRequest agenda2 = AgendaPadraoRequest.builder()
                .diaSemana(2)
                .horaInicio("09:00")
                .horaFim("13:00")
                .build();

        agendas = List.of(agenda1, agenda2);
    }

    @Test
    void deveConfigurarAgendaPadraoComSucesso() {
        agendaController.configuraAgendaPadrao(agendas, usuario);
        verify(agendaService, times(1)).configuraAgenda(anyList(), eq(usuario));
    }
}
