package com.agendou.agendou_api.profissional.application.service;

import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;
import com.agendou.agendou_api.profissional.application.repository.ConfiguracaoRepository;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ConfiguracaoApplicationService implements ConfiguracaoService {
    private final ConfiguracaoRepository configuracaoRepository;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public void configura(UUID id, ConfiguracaoProfissionalRequest configuracaoProfissionalRequest) {
        log.info("[start] ConfiguracaoApplicationService - configura");
        Profissional profissional = profissionalRepository.buscaPorIdUsuario(id);
        ConfiguracaoProfissional config = configuracaoRepository.buscarPorProfissionalId(profissional.getId())
                .orElseGet(ConfiguracaoProfissional::new);
        config.atualiza(configuracaoProfissionalRequest,profissional);
        configuracaoRepository.salvar(config);
        log.debug("[finish] ConfiguracaoApplicationService - configura");
    }
}
