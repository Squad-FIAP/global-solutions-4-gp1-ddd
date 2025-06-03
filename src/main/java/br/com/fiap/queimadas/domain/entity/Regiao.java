package br.com.fiap.queimadas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma região geográfica monitorada
 */
@Entity
@Table(name = "TB_REGIAO")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo", nullable = false)
    private String tipo; // Parque, Reserva, Área Rural, Área Urbana, etc.

    @Column(name = "area_metros_quadrados")
    private Double areaMetrosQuadrados;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "nivel_risco")
    private Integer nivelRisco; // 1-5, onde 5 é o mais alto

    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PontoFoco> pontosFoco = new ArrayList<>();

    /**
     * Adiciona um ponto de foco à região
     * @param pontoFoco Ponto de foco a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
     */
    public boolean adicionarPontoFoco(PontoFoco pontoFoco) {
        pontoFoco.setRegiao(this);
        return pontosFoco.add(pontoFoco);
    }

    /**
     * Calcula o número total de pontos de foco ativos na região
     * @return Total de pontos de foco ativos
     */
    public long calcularTotalPontosFocoAtivos() {
        return pontosFoco.stream()
                .filter(PontoFoco::isAtivo)
                .count();
    }

    /**
     * Recalcula o nível de risco da região baseado na quantidade e intensidade de incêndios
     */
    public void recalcularNivelRisco() {
        long totalAtivos = calcularTotalPontosFocoAtivos();
        // Cálculo simplificado para exemplo
        if (totalAtivos == 0) {
            this.nivelRisco = 1;
        } else if (totalAtivos < 3) {
            this.nivelRisco = 2;
        } else if (totalAtivos < 5) {
            this.nivelRisco = 3;
        } else if (totalAtivos < 10) {
            this.nivelRisco = 4;
        } else {
            this.nivelRisco = 5;
        }
    }
}
