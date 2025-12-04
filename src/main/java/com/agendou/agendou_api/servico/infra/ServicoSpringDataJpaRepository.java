package com.agendou.agendou_api.servico.infra;

import com.agendou.agendou_api.servico.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServicoSpringDataJpaRepository extends JpaRepository<Servico, UUID> {
    List<Servico> findByProfissionalId(UUID profissionalId);
}

