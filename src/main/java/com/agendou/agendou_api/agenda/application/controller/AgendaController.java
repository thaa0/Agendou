package com.agendou.agendou_api.agenda.application.controller;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.agenda.application.service.AgendaService;
import com.agendou.agendou_api.agenda.domain.AgendaPadrao;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/agenda")
public class AgendaController {
    private final AgendaService agendaService;

    @PostMapping()
    void configuraAgendaPadrao(@RequestBody List<AgendaPadraoRequest> agendas,
                               @AuthenticationPrincipal Usuario user){
        log.info("[start] AgendaController - configuraAgendaPadrao");
        agendaService.configuraAgenda(agendas, user);
        log.debug("[finish] AgendaController - configuraAgendaPadrao");
    }
}
