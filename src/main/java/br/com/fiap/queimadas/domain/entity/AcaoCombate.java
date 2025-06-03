package br.com.fiap.queimadas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe que representa uma ação tomada para combater um incêndio
 */
@Entity
@Table(name = "TB_ACAO_COMBATE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoCombate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ponto_foco_id", nullable = false)
    private PontoFoco pontoFoco;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "tipo_acao", nullable = false)
    private String tipoAcao; // Exemplo: "Combate terrestre", "Combate aéreo", "Monitoramento"

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "recursos_utilizados")
    private String recursosUtilizados;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "responsavel")
    private String responsavel;

    /**
     * Marca a ação de combate como concluída
     * @param resultado O resultado da ação
     */
    public void concluirAcao(String resultado) {
        this.dataFim = LocalDateTime.now();
        this.resultado = resultado;
    }

    /**
     * Verifica se a ação está em andamento
     * @return true se estiver em andamento, false se concluída
     */
    public boolean isEmAndamento() {
        return dataFim == null;
    }

    /**
     * Calcula a duração da ação em horas
     * @return Duração em horas, ou null se a ação ainda estiver em andamento
     */
    public Double calcularDuracaoHoras() {
        if (dataFim == null) {
            return null;
        }
        
        long segundos = java.time.Duration.between(dataInicio, dataFim).getSeconds();
        return segundos / 3600.0;
    }
}
