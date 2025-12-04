package com.agendou.agendou_api.profissional.application.controller;

import com.agendou.agendou_api.profissional.application.controller.dto.ProfissionalRequest;
import com.agendou.agendou_api.profissional.application.service.ProfissionalService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/profissional")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    void finalizaCadastro(@RequestBody ProfissionalRequest profissionalRequest,
                          @AuthenticationPrincipal Usuario user){
        log.info("[start] ProfissionalController - finalizaCadastro");
        profissionalService.finalizaCadastro(user.getId(),profissionalRequest);
        log.debug("[finish] ProfissionalController - finalizaCadastro");
    }
}
