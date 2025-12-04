package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.profissional.application.repository.ConfiguracaoRepository;
import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ConfiguracaoInfraRepository implements ConfiguracaoRepository {
    private final ConfiguracaoSpringDataJpaRepository configuracaoSpringDataJpaRepository;

    @Override
    public Optional<ConfiguracaoProfissional> buscarPorProfissionalId(UUID id) {
        log.info("[start] ConfiguracaoInfraRepository - buscarPorProfissionalId - {}", id);
        return configuracaoSpringDataJpaRepository.findByProfissionalId(id);
    }

    @Override
    public void salvar(ConfiguracaoProfissional config) {
        log.info("[start] ConfiguracaoInfraRepository - salvar");
        configuracaoSpringDataJpaRepository.save(config);
        log.debug("[finish] ConfiguracaoInfraRepository - salvar");
    }
}
