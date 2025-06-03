package br.com.fiap.queimadas.domain.entity;

import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe que representa um ponto de foco de incêndio detectado
 */
@Entity
@Table(name = "TB_PONTO_FOCO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoFoco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "data_deteccao", nullable = false)
    private LocalDateTime dataDeteccao;

    @Column(name = "intensidade")
    private Double intensidade;

    @Column(name = "area_estimada_metros_quadrados")
    private Double areaEstimadaMetrosQuadrados;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPontoFoco status;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regiao_id")
    private Regiao regiao;

    /**
     * Método para atualizar o status do ponto de foco
     * @param novoStatus Novo status do ponto de foco
     */
    public void atualizarStatus(StatusPontoFoco novoStatus) {
        this.status = novoStatus;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se o ponto de foco está ativo (não resolvido ou falso alarme)
     * @return true se estiver ativo, false caso contrário
     */
    public boolean isAtivo() {
        return status != StatusPontoFoco.RESOLVIDO && status != StatusPontoFoco.FALSO_ALARME;
    }
}
