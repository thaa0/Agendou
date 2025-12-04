package com.agendou.agendou_api.agenda.infra;

import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgendaSpringDataJpaRepository extends JpaRepository<AgendaPadrao,UUID> {
    List<AgendaPadrao> findAllByProfissionalId(UUID id);
}
