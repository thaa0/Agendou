package com.agendou.agendou_api.servico.application.controller;

import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoResponse;
import com.agendou.agendou_api.servico.application.service.ServicoService;
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
@RequestMapping("/v1/servico")
public class ServicoController {
    private final ServicoService servicoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ServicoResponse cadastra(@RequestBody @Valid ServicoRequest servicoRequest,
                             @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] ServicoController - cadastra");
        ServicoResponse response = servicoService.cadastra(usuario.getId(), servicoRequest);
        log.debug("[finish] ServicoController - cadastra");
        return response;
    }

    @PutMapping("/{servicoId}")
    ServicoResponse atualiza(@PathVariable UUID servicoId,
                            @RequestBody @Valid ServicoRequest servicoRequest,
                            @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] ServicoController - atualiza");
        ServicoResponse response = servicoService.atualiza(usuario.getId(), servicoId, servicoRequest);
        log.debug("[finish] ServicoController - atualiza");
        return response;
    }

    @DeleteMapping("/{servicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleta(@PathVariable UUID servicoId,
                @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] ServicoController - deleta");
        servicoService.deleta(usuario.getId(), servicoId);
        log.debug("[finish] ServicoController - deleta");
    }

    @GetMapping
    List<ServicoResponse> listaPorProfissional(@AuthenticationPrincipal Usuario usuario) {
        log.info("[start] ServicoController - listaPorProfissional");
        List<ServicoResponse> response = servicoService.listaPorProfissional(usuario.getId());
        log.debug("[finish] ServicoController - listaPorProfissional");
        return response;
    }

    @GetMapping("/{servicoId}")
    ServicoResponse buscaPorId(@PathVariable UUID servicoId,
                               @AuthenticationPrincipal Usuario usuario) {
        log.info("[start] ServicoController - buscaPorId");
        ServicoResponse response = servicoService.buscaPorId(usuario.getId(), servicoId);
        log.debug("[finish] ServicoController - buscaPorId");
        return response;
    }
}

