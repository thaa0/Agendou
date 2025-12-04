package com.agendou.agendou_api.profissional.application.controller;

import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;
import com.agendou.agendou_api.profissional.application.service.ConfiguracaoService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/configuracao")
public class ConfiguracaoProfissionalController {

    private final ConfiguracaoService configuracaoService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    void configuraPoliticas(@RequestBody ConfiguracaoProfissionalRequest configuracaoProfissionalRequest,
                            @AuthenticationPrincipal Usuario usuario){
        log.info("[start] ConfiguracaoProfissionalController - configuraPoliticas");
        configuracaoService.configura(usuario.getId(),configuracaoProfissionalRequest);
        log.debug("[finish] ConfiguracaoProfissionalController - configuraPoliticas");
    }
}
