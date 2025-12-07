package com.agendou.agendou_api.agenda.infra;

import com.agendou.agendou_api.agenda.domain.AgendaExcecao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaExcecaoSpringDataJpaRepository extends JpaRepository<AgendaExcecao, UUID> {
    List<AgendaExcecao> findAllByProfissionalId(UUID profissionalId);
    Optional<AgendaExcecao> findByProfissionalIdAndDataExcecaoAndDiaInteiroFechado(UUID profissionalId, LocalDate dataExcecao, Boolean diaInteiroFechado);
    Optional<AgendaExcecao> findByProfissionalIdAndDataExcecaoAndHoraInicioAndHoraFim(UUID profissionalId, LocalDate dataExcecao, String horaInicio, String horaFim);
}

