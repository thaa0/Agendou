package com.agendou.agendou_api.agenda.application.service;

import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.usuario.domain.Usuario;

import java.util.List;

public interface AgendaService {
    void configuraAgenda(List<AgendaPadraoRequest> agendas, Usuario user);
}
