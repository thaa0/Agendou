package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.profissional.domain.ConfiguracaoProfissional;
import com.agendou.agendou_api.profissional.domain.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConfiguracaoSpringDataJpaRepository extends JpaRepository<ConfiguracaoProfissional,UUID> {
    Optional<ConfiguracaoProfissional> findByProfissionalId(UUID id);
}
