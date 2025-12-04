package com.agendou.agendou_api.servico.domain;

import com.agendou.agendou_api.profissional.domain.Profissional;
import com.agendou.agendou_api.servico.application.controller.dto.ServicoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional não deve ser nulo")
    private Profissional profissional;

    @NotBlank(message = "Nome não deve estar em branco")
    private String nome;

    private String descricao;

    @NotNull(message = "Duração não deve ser nula")
    @Positive(message = "Duração deve ser maior que zero")
    @Column(name = "duracao_min")
    private Integer duracaoMin;

    public void atualiza(ServicoRequest servicoRequest) {
        this.nome = servicoRequest.nome();
        this.descricao = servicoRequest.descricao();
        this.duracaoMin = servicoRequest.duracaoMin();
    }
}


