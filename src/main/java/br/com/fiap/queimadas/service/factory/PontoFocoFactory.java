package br.com.fiap.queimadas.service.factory;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;

import java.time.LocalDateTime;

/**
 * Factory para criação de objetos PontoFoco com configurações padrão
 * Implementa o padrão Factory Method para encapsular a criação de pontos de foco
 */
public class PontoFocoFactory {

    /**
     * Cria um novo ponto de foco com valores padrão
     * @param latitude Latitude da localização
     * @param longitude Longitude da localização
     * @return Novo objeto PontoFoco
     */
    public static PontoFoco criarPontoFoco(Double latitude, Double longitude) {
        return PontoFoco.builder()
                .latitude(latitude)
                .longitude(longitude)
                .dataDeteccao(LocalDateTime.now())
                .status(StatusPontoFoco.NOVO)
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }

    /**
     * Cria um novo ponto de foco com intensidade
     * @param latitude Latitude da localização
     * @param longitude Longitude da localização
     * @param intensidade Intensidade do foco de incêndio
     * @return Novo objeto PontoFoco
     */
    public static PontoFoco criarPontoFocoComIntensidade(Double latitude, Double longitude, Double intensidade) {
        return PontoFoco.builder()
                .latitude(latitude)
                .longitude(longitude)
                .intensidade(intensidade)
                .dataDeteccao(LocalDateTime.now())
                .status(StatusPontoFoco.NOVO)
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }

    /**
     * Cria um novo ponto de foco com área estimada
     * @param latitude Latitude da localização
     * @param longitude Longitude da localização
     * @param intensidade Intensidade do foco de incêndio
     * @param areaEstimadaMetrosQuadrados Área estimada do incêndio
     * @return Novo objeto PontoFoco
     */
    public static PontoFoco criarPontoFocoCompleto(Double latitude, Double longitude, 
                                              Double intensidade, Double areaEstimadaMetrosQuadrados,
                                              String descricao) {
        return PontoFoco.builder()
                .latitude(latitude)
                .longitude(longitude)
                .intensidade(intensidade)
                .areaEstimadaMetrosQuadrados(areaEstimadaMetrosQuadrados)
                .descricao(descricao)
                .dataDeteccao(LocalDateTime.now())
                .status(StatusPontoFoco.NOVO)
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }
}
