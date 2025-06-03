package br.com.fiap.queimadas.repository;

import br.com.fiap.queimadas.domain.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long> {

    /**
     * Busca regiões por nome contendo o texto informado
     */
    List<Regiao> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca regiões por tipo
     */
    List<Regiao> findByTipoIgnoreCase(String tipo);

    /**
     * Busca regiões com nível de risco acima do informado
     */
    List<Regiao> findByNivelRiscoGreaterThanEqual(Integer nivelRiscoMinimo);

    /**
     * Busca regiões ordenadas pelo número de pontos de foco ativos (descendente)
     */
    @Query("SELECT r FROM Regiao r LEFT JOIN r.pontosFoco p " +
           "WHERE p.status != 'RESOLVIDO' AND p.status != 'FALSO_ALARME' " +
           "GROUP BY r ORDER BY COUNT(p) DESC")
    List<Regiao> findAllOrderByPontosFocoAtivosDesc();

    /**
     * Busca regiões que não possuem pontos de foco ativos
     */
    @Query("SELECT r FROM Regiao r WHERE NOT EXISTS " +
           "(SELECT p FROM PontoFoco p WHERE p.regiao = r AND p.status != 'RESOLVIDO' AND p.status != 'FALSO_ALARME')")
    List<Regiao> findAllSemPontosFocoAtivos();
}
