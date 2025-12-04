package com.agendou.agendou_api.servico.infra;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.servico.application.repository.ServicoRepository;
import com.agendou.agendou_api.servico.domain.Servico;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ServicoInfraRepository implements ServicoRepository {
    private final ServicoSpringDataJpaRepository servicoSpringDataJpaRepository;

    @Override
    public Servico salva(Servico servico) {
        log.info("[start] ServicoInfraRepository - salva");
        Servico servicoSalvo = servicoSpringDataJpaRepository.save(servico);
        log.debug("[finish] ServicoInfraRepository - salva");
        return servicoSalvo;
    }

    @Override
    public Servico buscaPorId(UUID id) {
        log.info("[start] ServicoInfraRepository - buscaPorId: {}", id);
        Servico servico = servicoSpringDataJpaRepository.findById(id)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Serviço não encontrado para id: " + id));
        log.debug("[finish] ServicoInfraRepository - buscaPorId");
        return servico;
    }

    @Override
    public List<Servico> buscaPorProfissionalId(UUID profissionalId) {
        log.info("[start] ServicoInfraRepository - buscaPorProfissionalId: {}", profissionalId);
        List<Servico> servicos = servicoSpringDataJpaRepository.findByProfissionalId(profissionalId);
        log.debug("[finish] ServicoInfraRepository - buscaPorProfissionalId");
        return servicos;
    }

    @Override
    public void deleta(Servico servico) {
        log.info("[start] ServicoInfraRepository - deleta");
        servicoSpringDataJpaRepository.delete(servico);
        log.debug("[finish] ServicoInfraRepository - deleta");
    }
}

