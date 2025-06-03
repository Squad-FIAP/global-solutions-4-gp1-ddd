package br.com.fiap.queimadas.repository;

import br.com.fiap.queimadas.domain.entity.AcaoCombate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcaoCombateRepository extends JpaRepository<AcaoCombate, Long> {

    /**
     * Busca ações de combate por ponto de foco
     */
    List<AcaoCombate> findByPontoFocoId(Long pontoFocoId);

    /**
     * Busca ações de combate em andamento (dataFim é null)
     */
    List<AcaoCombate> findByDataFimIsNull();

    /**
     * Busca ações de combate por tipo de ação
     */
    List<AcaoCombate> findByTipoAcaoContainingIgnoreCase(String tipoAcao);

    /**
     * Busca ações de combate iniciadas após uma determinada data
     */
    List<AcaoCombate> findByDataInicioAfter(LocalDateTime data);

    /**
     * Busca ações de combate associadas a pontos de foco em uma determinada região
     */
    @Query("SELECT a FROM AcaoCombate a JOIN a.pontoFoco p WHERE p.regiao.id = :regiaoId")
    List<AcaoCombate> findByRegiaoId(Long regiaoId);

    /**
     * Busca ações de combate concluídas em um período
     */
    List<AcaoCombate> findByDataFimBetween(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Conta quantas ações de combate estão em andamento por região
     */
    @Query("SELECT COUNT(a) FROM AcaoCombate a JOIN a.pontoFoco p " +
           "WHERE p.regiao.id = :regiaoId AND a.dataFim IS NULL")
    Long countAcoesEmAndamentoByRegiaoId(Long regiaoId);
}
