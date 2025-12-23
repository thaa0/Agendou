package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProfissionalInfraRepository implements ProfissionalRepository {
    private final ProfissionalSpringDataJpaRepository profissionalSpringDataJpaRepository;

    @Override
    public Profissional salva(Profissional profissional) {
        log.info("[start] ProfissionalInfraRepository - salva");
        Profissional profis = profissionalSpringDataJpaRepository.save(profissional);
        log.debug("[finish] ProfissionalInfraRepository - salva");
        return profis;
    }

    @Override
    public Profissional buscaPorIdUsuario(UUID id) {
        log.info("[start] ProfissionalInfraRepository - buscaPorIdUsuario: {}", id);
        Profissional profissional = profissionalSpringDataJpaRepository.findByUsuarioId(id)
                        .orElseThrow(()-> APIException.build(HttpStatus.NOT_FOUND, "Profissional não encontrada para id: "+id));
        log.debug("[finish] ProfissionalInfraRepository - buscaPorIdUsuario");
        return profissional;
    }

    @Override
    public boolean existeSlug(String slug) {
        log.info("[start] ProfissionalInfraRepository - existeSlug: {}", slug);
        boolean existe = profissionalSpringDataJpaRepository.existsBySlug(slug);
        log.debug("[finish] ProfissionalInfraRepository - existeSlug");
        return existe;
    }
}