package com.agendou.agendou_api.profissional.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ProfissionalController {

    @PostMapping()
    void finalizaCadastro(ProfissionalRequest profissionalRequest){
        log.info("[start] ProfissionalController - finalizaCadastro");
        //chamada do service
        log.debug("[finish] ProfissionalController - finalizaCadastro");
    }
}
