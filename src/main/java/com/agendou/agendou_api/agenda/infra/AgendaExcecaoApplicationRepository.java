package com.agendou.agendou_api.agenda.infra;

import com.agendou.agendou_api.agenda.application.repository.AgendaExcecaoRepository;
import com.agendou.agendou_api.agenda.domain.AgendaExcecao;
import com.agendou.agendou_api.core.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class AgendaExcecaoApplicationRepository implements AgendaExcecaoRepository {
    private final AgendaExcecaoSpringDataJpaRepository agendaExcecaoSpringDataJpaRepository;

    @Override
    public AgendaExcecao salvar(AgendaExcecao excecao) {
        log.info("[start] AgendaExcecaoApplicationRepository - salvar");
        return agendaExcecaoSpringDataJpaRepository.save(excecao);
    }

    @Override
    public List<AgendaExcecao> buscarPorProfissionalId(UUID profissionalId) {
        log.info("[start] AgendaExcecaoApplicationRepository - buscarPorProfissionalId - {}", profissionalId);
        return agendaExcecaoSpringDataJpaRepository.findAllByProfissionalId(profissionalId);
    }

    @Override
    public AgendaExcecao buscarPorId(UUID id) {
        log.info("[start] AgendaExcecaoApplicationRepository - buscarPorId - {}", id);
        AgendaExcecao excecao = agendaExcecaoSpringDataJpaRepository.findById(id)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Exceção de agenda não encontrada para id: " + id));
        log.debug("[finish] AgendaExcecaoApplicationRepository - buscarPorId");
        return excecao;
    }

    @Override
    public void deletar(AgendaExcecao excecao) {
        log.info("[start] AgendaExcecaoApplicationRepository - deletar");
        agendaExcecaoSpringDataJpaRepository.delete(excecao);
    }

    @Override
    public void validarPropriedade(AgendaExcecao excecao, UUID profissionalId) {
        log.info("[start] AgendaExcecaoApplicationRepository - validarPropriedade");
        if (!excecao.getProfissional().getId().equals(profissionalId)) {
            throw APIException.build(HttpStatus.FORBIDDEN, "Exceção de agenda não pertence ao profissional");
        }
        log.debug("[finish] AgendaExcecaoApplicationRepository - validarPropriedade");
    }

    @Override
    public void validarDuplicidade(AgendaExcecao excecao) {
        log.info("[start] AgendaExcecaoApplicationRepository - validarDuplicidade");
        if (excecao.getDiaInteiroFechado()) {
            agendaExcecaoSpringDataJpaRepository
                    .findByProfissionalIdAndDataExcecaoAndDiaInteiroFechado(
                            excecao.getProfissional().getId(),
                            excecao.getDataExcecao(),
                            true
                    )
                    .ifPresent(e -> {
                        throw APIException.build(HttpStatus.CONFLICT,
                                "Já existe uma exceção de dia inteiro fechado para esta data");
                    });
        } else {
            agendaExcecaoSpringDataJpaRepository
                    .findByProfissionalIdAndDataExcecaoAndHoraInicioAndHoraFim(
                            excecao.getProfissional().getId(),
                            excecao.getDataExcecao(),
                            excecao.getHoraInicio(),
                            excecao.getHoraFim()
                    )
                    .ifPresent(e -> {
                        throw APIException.build(HttpStatus.CONFLICT,
                                "Já existe uma exceção para esta data e horário");
                    });
        }
        log.debug("[finish] AgendaExcecaoApplicationRepository - validarDuplicidade");
    }
}
