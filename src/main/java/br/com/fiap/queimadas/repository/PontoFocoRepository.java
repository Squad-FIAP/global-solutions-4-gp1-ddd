package br.com.fiap.queimadas.repository;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PontoFocoRepository extends JpaRepository<PontoFoco, Long> {

    /**
     * Busca pontos de foco por status
     */
    List<PontoFoco> findByStatus(StatusPontoFoco status);

    /**
     * Busca pontos de foco por região
     */
    List<PontoFoco> findByRegiaoId(Long regiaoId);

    /**
     * Busca pontos de foco ativos (não resolvidos ou falsos alarmes)
     */
    @Query("SELECT p FROM PontoFoco p WHERE p.status != 'RESOLVIDO' AND p.status != 'FALSO_ALARME'")
    List<PontoFoco> findAllAtivos();

    /**
     * Busca pontos de foco detectados após uma determinada data
     */
    List<PontoFoco> findByDataDeteccaoAfter(LocalDateTime data);

    /**
     * Busca pontos de foco por coordenadas próximas
     */
    @Query("SELECT p FROM PontoFoco p WHERE " +
           "p.latitude BETWEEN :latitude - :raioGraus AND :latitude + :raioGraus AND " +
           "p.longitude BETWEEN :longitude - :raioGraus AND :longitude + :raioGraus")
    List<PontoFoco> findByProximidade(Double latitude, Double longitude, Double raioGraus);

    /**
     * Conta quantos pontos de foco estão ativos por região
     */
    @Query("SELECT COUNT(p) FROM PontoFoco p WHERE p.regiao.id = :regiaoId AND " +
           "p.status != 'RESOLVIDO' AND p.status != 'FALSO_ALARME'")
    Long countAtivosByRegiaoId(Long regiaoId);
    
    /**
     * Lista pontos de foco por intensidade acima de um valor
     */
    List<PontoFoco> findByIntensidadeGreaterThanOrderByIntensidadeDesc(Double intensidadeMinima);
}
