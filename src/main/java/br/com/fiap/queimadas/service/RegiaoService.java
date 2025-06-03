package br.com.fiap.queimadas.service;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.entity.Regiao;
import br.com.fiap.queimadas.repository.RegiaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de regiões monitoradas
 */
@Service
public class RegiaoService {

    private final RegiaoRepository regiaoRepository;

    @Autowired
    public RegiaoService(RegiaoRepository regiaoRepository) {
        this.regiaoRepository = regiaoRepository;
    }

    /**
     * Lista todas as regiões
     */
    public List<Regiao> listarTodas() {
        return regiaoRepository.findAll();
    }

    /**
     * Busca uma região pelo ID
     */
    public Optional<Regiao> buscarPorId(Long id) {
        return regiaoRepository.findById(id);
    }

    /**
     * Lista regiões por nome
     */
    public List<Regiao> buscarPorNome(String nome) {
        return regiaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Lista regiões por tipo
     */
    public List<Regiao> buscarPorTipo(String tipo) {
        return regiaoRepository.findByTipoIgnoreCase(tipo);
    }

    /**
     * Lista regiões por nível de risco
     */
    public List<Regiao> listarPorNivelRiscoMinimo(Integer nivelRiscoMinimo) {
        return regiaoRepository.findByNivelRiscoGreaterThanEqual(nivelRiscoMinimo);
    }

    /**
     * Lista regiões ordenadas pelo número de pontos de foco ativos
     */
    public List<Regiao> listarPorPontosFocoAtivos() {
        return regiaoRepository.findAllOrderByPontosFocoAtivosDesc();
    }

    /**
     * Lista regiões sem pontos de foco ativos
     */
    public List<Regiao> listarRegioesSemPontosFocoAtivos() {
        return regiaoRepository.findAllSemPontosFocoAtivos();
    }

    /**
     * Registra uma nova região
     */
    @Transactional
    public Regiao registrarRegiao(Regiao regiao) {
        // Define o nível de risco inicial como 1 (mais baixo)
        if (regiao.getNivelRisco() == null) {
            regiao.setNivelRisco(1);
        }
        return regiaoRepository.save(regiao);
    }

    /**
     * Atualiza uma região existente
     */
    @Transactional
    public Optional<Regiao> atualizarRegiao(Long id, Regiao regiaoAtualizada) {
        Optional<Regiao> regiaoExistenteOpt = regiaoRepository.findById(id);
        
        if (regiaoExistenteOpt.isPresent()) {
            Regiao regiaoExistente = regiaoExistenteOpt.get();
            
            // Atualiza os campos da região
            regiaoExistente.setNome(regiaoAtualizada.getNome());
            regiaoExistente.setTipo(regiaoAtualizada.getTipo());
            regiaoExistente.setAreaMetrosQuadrados(regiaoAtualizada.getAreaMetrosQuadrados());
            regiaoExistente.setDescricao(regiaoAtualizada.getDescricao());
            
            // Não atualiza automaticamente o nível de risco ou pontos de foco
            
            return Optional.of(regiaoRepository.save(regiaoExistente));
        }
        
        return Optional.empty();
    }

    /**
     * Adiciona um ponto de foco a uma região
     */
    @Transactional
    public Optional<Regiao> adicionarPontoFoco(Long regiaoId, PontoFoco pontoFoco) {
        Optional<Regiao> regiaoOpt = regiaoRepository.findById(regiaoId);
        
        if (regiaoOpt.isPresent()) {
            Regiao regiao = regiaoOpt.get();
            regiao.adicionarPontoFoco(pontoFoco);
            regiao.recalcularNivelRisco();
            return Optional.of(regiaoRepository.save(regiao));
        }
        
        return Optional.empty();
    }

    /**
     * Remove uma região
     */
    @Transactional
    public boolean removerRegiao(Long id) {
        if (regiaoRepository.existsById(id)) {
            regiaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recalcula o nível de risco de uma região
     */
    @Transactional
    public Optional<Regiao> recalcularNivelRisco(Long id) {
        Optional<Regiao> regiaoOpt = regiaoRepository.findById(id);
        
        if (regiaoOpt.isPresent()) {
            Regiao regiao = regiaoOpt.get();
            regiao.recalcularNivelRisco();
            return Optional.of(regiaoRepository.save(regiao));
        }
        
        return Optional.empty();
    }
}
