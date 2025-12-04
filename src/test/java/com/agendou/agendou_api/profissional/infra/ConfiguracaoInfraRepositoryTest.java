package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.profissional.application.repository.ConfiguracaoRepository;
import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfiguracaoInfraRepositoryTest {

    @Mock
    private ConfiguracaoSpringDataJpaRepository jpa;

    @InjectMocks
    private ConfiguracaoInfraRepository repository;

    private UUID profissionalId;
    private ConfiguracaoProfissional config;

    @BeforeEach
    void setup() {
        profissionalId = UUID.randomUUID();
        config = new ConfiguracaoProfissional();
    }

    @Test
    void deveBuscarConfiguracaoPorProfissionalId() {
        when(jpa.findByProfissionalId(profissionalId)).thenReturn(Optional.of(config));

        Optional<ConfiguracaoProfissional> result = repository.buscarPorProfissionalId(profissionalId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(config);
        verify(jpa).findByProfissionalId(profissionalId);
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoExistirConfiguracao() {
        when(jpa.findByProfissionalId(profissionalId)).thenReturn(Optional.empty());

        Optional<ConfiguracaoProfissional> result = repository.buscarPorProfissionalId(profissionalId);

        assertThat(result).isEmpty();
        verify(jpa).findByProfissionalId(profissionalId);
    }

    @Test
    void deveSalvarConfiguracao() {
        repository.salvar(config);

        verify(jpa).save(config);
    }
}
