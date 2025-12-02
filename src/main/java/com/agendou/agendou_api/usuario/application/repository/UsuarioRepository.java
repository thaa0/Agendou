package com.agendou.agendou_api.usuario.application.repository;


import com.agendou.agendou_api.usuario.domain.Usuario;

public interface UsuarioRepository {
    void salva(Usuario usuario);
    Usuario buscaUsuario(String email);
}
