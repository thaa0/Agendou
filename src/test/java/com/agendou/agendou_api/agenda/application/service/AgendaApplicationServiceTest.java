package com.agendou.agendou_api.agenda.application.service;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.agenda.application.repository.AgendaRepository;
import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaApplicationServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private AgendaApplicationService agendaService;

    private Usuario usuario;
    private Profissional profissional;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().id(UUID.randomUUID()).build();
        profissional = Profissional.builder().id(UUID.randomUUID()).build();
        when(profissionalRepository.buscaPorIdUsuario(usuario.getId())).thenReturn(profissional);
    }

    @Test
    void deveSalvarNovasAgendasQuandoNaoExistem() {
        when(agendaRepository.buscarPorProfissionalId(profissional.getId()))
                .thenReturn(Collections.emptyList());

        List<AgendaPadraoRequest> requests = Arrays.asList(
                new AgendaPadraoRequest(1, "09:00", "12:00"),
                new AgendaPadraoRequest(2, "10:00", "14:00")
        );

        agendaService.configuraAgenda(requests, usuario);

        verify(agendaRepository, times(2)).salvar(any(AgendaPadrao.class));
    }

    @Test
    void deveAtualizarAgendasExistentes() {
        AgendaPadrao agendaExistente = AgendaPadrao.builder().diaSemana(1).build();
        Map<Integer, AgendaPadrao> map = new HashMap<>();
        map.put(1, agendaExistente);

        when(agendaRepository.buscarPorProfissionalId(profissional.getId()))
                .thenReturn(List.of(agendaExistente));

        List<AgendaPadraoRequest> requests = Collections.singletonList(
                new AgendaPadraoRequest(1, "08:00", "11:00")
        );

        agendaService.configuraAgenda(requests, usuario);

        verify(agendaRepository).salvar(agendaExistente);
        assert(agendaExistente.getHoraInicio().equals("08:00"));
        assert(agendaExistente.getHoraFim().equals("11:00"));
    }

    @Test
    void deveTratarDuplicatasNaRequisicao() {
        when(agendaRepository.buscarPorProfissionalId(profissional.getId()))
                .thenReturn(Collections.emptyList());

        List<AgendaPadraoRequest> requests = Arrays.asList(
                new AgendaPadraoRequest(3, "09:00", "12:00"),
                new AgendaPadraoRequest(3, "10:00", "13:00") // duplicata, última vence
        );

        agendaService.configuraAgenda(requests, usuario);

        ArgumentCaptor<AgendaPadrao> captor = ArgumentCaptor.forClass(AgendaPadrao.class);
        verify(agendaRepository, times(1)).salvar(captor.capture());

        AgendaPadrao salvo = captor.getValue();
        assert(salvo.getDiaSemana() == 3);
        assert(salvo.getHoraInicio().equals("10:00"));
        assert(salvo.getHoraFim().equals("13:00"));
    }
}
