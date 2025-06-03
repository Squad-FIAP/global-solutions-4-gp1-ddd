package br.com.fiap.queimadas.service.factory;

import br.com.fiap.queimadas.domain.entity.AcaoCombate;
import br.com.fiap.queimadas.domain.entity.PontoFoco;

import java.time.LocalDateTime;

/**
 * Factory para criação de objetos AcaoCombate
 * Encapsula a criação de diferentes tipos de ações de combate
 */
public class AcaoCombateFactory {

    /**
     * Cria uma ação de combate terrestre
     * @param pontoFoco Ponto de foco a ser combatido
     * @param descricao Descrição da ação
     * @param responsavel Responsável pela ação
     * @return Nova ação de combate
     */
    public static AcaoCombate criarAcaoCombateTerrestre(PontoFoco pontoFoco, String descricao, String responsavel) {
        return AcaoCombate.builder()
                .pontoFoco(pontoFoco)
                .tipoAcao("Combate terrestre")
                .dataInicio(LocalDateTime.now())
                .descricao(descricao)
                .responsavel(responsavel)
                .recursosUtilizados("Brigada terrestre, caminhões-pipa, abafadores")
                .build();
    }

    /**
     * Cria uma ação de combate aéreo
     * @param pontoFoco Ponto de foco a ser combatido
     * @param descricao Descrição da ação
     * @param responsavel Responsável pela ação
     * @return Nova ação de combate
     */
    public static AcaoCombate criarAcaoCombateAereo(PontoFoco pontoFoco, String descricao, String responsavel) {
        return AcaoCombate.builder()
                .pontoFoco(pontoFoco)
                .tipoAcao("Combate aéreo")
                .dataInicio(LocalDateTime.now())
                .descricao(descricao)
                .responsavel(responsavel)
                .recursosUtilizados("Aeronaves, lançamento de água/retardante")
                .build();
    }

    /**
     * Cria uma ação de monitoramento
     * @param pontoFoco Ponto de foco a ser monitorado
     * @param descricao Descrição da ação
     * @param responsavel Responsável pela ação
     * @return Nova ação de monitoramento
     */
    public static AcaoCombate criarAcaoMonitoramento(PontoFoco pontoFoco, String descricao, String responsavel) {
        return AcaoCombate.builder()
                .pontoFoco(pontoFoco)
                .tipoAcao("Monitoramento")
                .dataInicio(LocalDateTime.now())
                .descricao(descricao)
                .responsavel(responsavel)
                .recursosUtilizados("Monitoramento por satélite, drones, equipe de vigilância")
                .build();
    }

    /**
     * Cria uma ação de combate personalizada
     * @param pontoFoco Ponto de foco
     * @param tipoAcao Tipo da ação
     * @param descricao Descrição da ação
     * @param responsavel Responsável pela ação
     * @param recursosUtilizados Recursos utilizados
     * @return Nova ação de combate
     */
    public static AcaoCombate criarAcaoPersonalizada(PontoFoco pontoFoco, String tipoAcao, 
                                                String descricao, String responsavel,
                                                String recursosUtilizados) {
        return AcaoCombate.builder()
                .pontoFoco(pontoFoco)
                .tipoAcao(tipoAcao)
                .dataInicio(LocalDateTime.now())
                .descricao(descricao)
                .responsavel(responsavel)
                .recursosUtilizados(recursosUtilizados)
                .build();
    }
}
