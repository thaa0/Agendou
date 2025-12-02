package com.agendou.agendou_api.profissional.domain;

import com.agendou.agendou_api.usuario.domain.Usuario;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Profissional{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String descricao;

    @OneToOne(mappedBy = "profissional")
    private ConfiguracaoProfissional configuracao;

    public Profissional(Usuario user) {
        this.usuario = user;
    }
}