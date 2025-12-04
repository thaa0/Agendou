package com.agendou.agendou_api.profissional.domain;

import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    public void atualiza(ConfiguracaoProfissionalRequest configuracaoProfissionalRequest, Profissional profissional) {
        this.profissional = profissional;
        this.intervaloCancelamentoHoras = configuracaoProfissionalRequest.intervaloCancelamentoHoras();
        this.msgLembreteAtendimento = configuracaoProfissionalRequest.msgLembreteAtendimento();
        this.msgPosAtendimento = configuracaoProfissionalRequest.msgPosAtendimento();
        this.ultimaAtualizacao = LocalDateTime.now();
    }
}