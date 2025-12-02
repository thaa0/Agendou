package com.agendou.agendou_api.profissional.infra;

import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

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
}
