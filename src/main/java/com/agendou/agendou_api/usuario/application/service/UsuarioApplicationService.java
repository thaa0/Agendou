package com.agendou.agendou_api.usuario.application.service;

import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.application.repository.UsuarioRepository;
import com.agendou.agendou_api.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class UsuarioApplicationService implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encriptador;
    private final ProfissionalRepository profissionalRepository;

    @Override
    public void cadastrarUsuario(UsuarioRequest request) {
        log.info("[start] UsuarioApplicationService - cadastrarUsuario");
        Usuario usuario = new Usuario(request, encriptador);
        Profissional profissional = new Profissional(usuario);
        usuarioRepository.salva(usuario);
        profissionalRepository.salva(profissional);
        log.debug("[finish] UsuarioApplicationService - cadastrarUsuario");
    }
}
