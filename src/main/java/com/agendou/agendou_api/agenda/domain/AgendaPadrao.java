package com.agendou.agendou_api.agenda.domain;
import com.agendou.agendou_api.agenda.application.controller.dto.AgendaPadraoRequest;
import com.agendou.agendou_api.profissional.domain.Profissional;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class AgendaPadrao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @Column(name = "dia_semana", nullable = false)
    private int diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private String horaFim;

    public void atualiza(AgendaPadraoRequest req, Profissional profissional) {
        this.profissional = profissional;
        this.diaSemana = req.getDiaSemana();
        this.horaInicio = req.getHoraInicio();
        this.horaFim = req.getHoraFim();
    }
}