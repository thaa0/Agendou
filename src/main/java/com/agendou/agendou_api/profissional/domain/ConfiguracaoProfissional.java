package com.agendou.agendou_api.profissional.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ConfiguracaoProfissional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "profissional_id", unique = true, nullable = false)
    private Profissional profissional;
    @NotNull(message = "Intervalo nao deve ser nulo")
    private int intervaloCancelamentoHoras;
    private String msgLembreteAtendimento;
    private String msgPosAtendimento;
    private LocalDateTime ultimaAtualizacao;
}