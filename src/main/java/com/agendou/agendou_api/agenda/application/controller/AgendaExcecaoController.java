package com.agendou.agendou_api.agenda.application.controller;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoRequest;
import com.agendou.agendou_api.agenda.application.controller.dto.AgendaExcecaoResponse;
import com.agendou.agendou_api.agenda.application.service.AgendaExcecaoService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/agenda/excecao")
public class AgendaExcecaoController {
    private final AgendaExcecaoService agendaExcecaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AgendaExcecaoResponse criarExcecao(@Valid @RequestBody AgendaExcecaoRequest request,
                                       @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] AgendaExcecaoController - criarExcecao - usuarioId: {}", usuario.getId());
        AgendaExcecaoResponse response = agendaExcecaoService.criarExcecao(usuario.getId(), request);
        log.debug("[finish] AgendaExcecaoController - criarExcecao");
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AgendaExcecaoResponse> listarExcecoes(@AuthenticationPrincipal Usuario usuario) {
        log.info("[start] AgendaExcecaoController - listarExcecoes - usuarioId: {}", usuario.getId());
        List<AgendaExcecaoResponse> response = agendaExcecaoService.listarExcecoes(usuario.getId());
        log.debug("[finish] AgendaExcecaoController - listarExcecoes");
        return response;
    }

    @DeleteMapping("/{excecaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletarExcecao(@PathVariable UUID excecaoId,
                        @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] AgendaExcecaoController - deletarExcecao - usuarioId: {}, excecaoId: {}", 
                usuario.getId(), excecaoId);
        agendaExcecaoService.deletarExcecao(usuario.getId(), excecaoId);
        log.debug("[finish] AgendaExcecaoController - deletarExcecao");
    }
}

