package br.com.fiap.queimadas.domain.enums;

/**
 * Enum que representa os possíveis status de um ponto de foco de incêndio
 */
public enum StatusPontoFoco {
    /**
     * Ponto de foco recém detectado, ainda não confirmado ou avaliado
     */
    NOVO,
    
    /**
     * Ponto de foco confirmado e aguardando ação de combate
     */
    CONFIRMADO,
    
    /**
     * Ponto de foco em fase de avaliação ou triagem
     */
    EM_AVALIACAO,
    
    /**
     * Ponto de foco em combate ativo
     */
    EM_COMBATE,
    
    /**
     * Ponto de foco em monitoramento após combate inicial ou para pequenos focos
     */
    MONITORAMENTO,
    
    /**
     * Ponto de foco controlado, mas ainda em observação
     */
    CONTROLADO,
    
    /**
     * Ponto de foco extinto/resolvido
     */
    RESOLVIDO,
    
    /**
     * Detecção classificada como falso alarme
     */
    FALSO_ALARME
}
