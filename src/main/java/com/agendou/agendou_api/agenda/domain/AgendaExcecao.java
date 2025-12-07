package com.agendou.agendou_api.agenda.domain;

import com.agendou.agendou_api.profissional.domain.Profissional;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "agenda_excecao")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AgendaExcecao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @Column(name = "data_excecao", nullable = false)
    private LocalDate dataExcecao;

    @Column(name = "hora_inicio")
    private String horaInicio;

    @Column(name = "hora_fim")
    private String horaFim;

    @Column(name = "dia_inteiro_fechado", nullable = false)
    private Boolean diaInteiroFechado;

    public static AgendaExcecao criarExcecao(Profissional profissional, LocalDate dataExcecao,
                                             String horaInicio, String horaFim, Boolean diaInteiroFechado) {
        return AgendaExcecao.builder()
                .profissional(profissional)
                .dataExcecao(dataExcecao)
                .horaInicio(horaInicio)
                .horaFim(horaFim)
                .diaInteiroFechado(diaInteiroFechado)
                .build();
    }
}
