package com.agendou.agendou_api.profissional.application.controller.dto;

import com.agendou.agendou_api.usuario.domain.TipoPlano;
import com.agendou.agendou_api.usuario.domain.Usuario;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nomeCompleto,
        String nomeFantasia,
        String email,
        String whatsapp,
        TipoPlano tipo
) {
    public UsuarioResponse(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getNomeFantasia(),
                usuario.getEmail(),
                usuario.getWhatsapp(),
                usuario.getTipo()
        );
    }
}

