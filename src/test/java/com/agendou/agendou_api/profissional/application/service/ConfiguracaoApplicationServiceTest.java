package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;
import com.agendou.agendou_api.profissional.application.repository.ConfiguracaoRepository;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;
import com.agendou.agendou_api.profissional.domain.Profissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfiguracaoApplicationServiceTest {

    @Mock
    private ConfiguracaoRepository configuracaoRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ConfiguracaoApplicationService service;

    private UUID userId;
    private Profissional profissional;
    private ConfiguracaoProfissionalRequest request;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        profissional = Profissional.builder().id(UUID.randomUUID()).build();

        request = new ConfiguracaoProfissionalRequest(
                24,
                "Lembrete",
                "Mensagem pós atendimento"
        );
    }

    @Test
    void deveCriarNovaConfiguracaoQuandoNaoExistir() {
        when(profissionalRepository.buscaPorIdUsuario(userId)).thenReturn(profissional);
        when(configuracaoRepository.buscarPorProfissionalId(profissional.getId()))
                .thenReturn(Optional.empty());

        service.configura(userId, request);

        verify(configuracaoRepository).salvar(any(ConfiguracaoProfissional.class));
    }

    @Test
    void deveAtualizarConfiguracaoExistente() {
        ConfiguracaoProfissional existente = new ConfiguracaoProfissional();

        when(profissionalRepository.buscaPorIdUsuario(userId)).thenReturn(profissional);
        when(configuracaoRepository.buscarPorProfissionalId(profissional.getId()))
                .thenReturn(Optional.of(existente));

        service.configura(userId, request);

        verify(configuracaoRepository).salvar(existente);
    }

    @Test
    void deveLancarErroQuandoProfissionalNaoExistir() {
        when(profissionalRepository.buscaPorIdUsuario(userId))
                .thenThrow(APIException.build(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        try {
            service.configura(userId, request);
        } catch (APIException ignored) {}

        verify(configuracaoRepository, never()).salvar(any());
    }
}
