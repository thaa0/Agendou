package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.profissional.domain.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfissionalSpringDataJpaRepository extends JpaRepository<Profissional,UUID> {
}
