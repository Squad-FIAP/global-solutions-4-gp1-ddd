package br.com.fiap.queimadas.service;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.entity.Regiao;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import br.com.fiap.queimadas.repository.PontoFocoRepository;
import br.com.fiap.queimadas.repository.RegiaoRepository;
import br.com.fiap.queimadas.service.factory.PontoFocoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de pontos de foco de incêndio
 */
@Service
public class PontoFocoService {

    private final PontoFocoRepository pontoFocoRepository;
    private final RegiaoRepository regiaoRepository;

    @Autowired
    public PontoFocoService(PontoFocoRepository pontoFocoRepository, RegiaoRepository regiaoRepository) {
        this.pontoFocoRepository = pontoFocoRepository;
        this.regiaoRepository = regiaoRepository;
    }

    /**
     * Lista todos os pontos de foco
     */
    public List<PontoFoco> listarTodos() {
        return pontoFocoRepository.findAll();
    }

    /**
     * Lista pontos de foco por status
     */
    public List<PontoFoco> listarPorStatus(StatusPontoFoco status) {
        return pontoFocoRepository.findByStatus(status);
    }

    /**
     * Lista pontos de foco ativos (não resolvidos ou falsos alarmes)
     */
    public List<PontoFoco> listarAtivos() {
        return pontoFocoRepository.findAllAtivos();
    }

    /**
     * Busca um ponto de foco pelo ID
     */
    public Optional<PontoFoco> buscarPorId(Long id) {
        return pontoFocoRepository.findById(id);
    }

    /**
     * Lista pontos de foco por região
     */
    public List<PontoFoco> listarPorRegiao(Long regiaoId) {
        return pontoFocoRepository.findByRegiaoId(regiaoId);
    }

    /**
     * Registra um novo ponto de foco com dados básicos
     */
    @Transactional
    public PontoFoco registrarPontoFoco(Double latitude, Double longitude, Long regiaoId) {
        // Cria o ponto de foco usando a factory
        PontoFoco pontoFoco = PontoFocoFactory.criarPontoFoco(latitude, longitude);
        
        // Associa à região, se informada
        if (regiaoId != null) {
            Optional<Regiao> regiaoOpt = regiaoRepository.findById(regiaoId);
            regiaoOpt.ifPresent(pontoFoco::setRegiao);
        }
        
        // Salva e retorna o ponto de foco criado
        return pontoFocoRepository.save(pontoFoco);
    }

    /**
     * Registra um novo ponto de foco com todos os dados
     */
    @Transactional
    public PontoFoco registrarPontoFocoCompleto(Double latitude, Double longitude, 
                                            Double intensidade, Double areaEstimada,
                                            String descricao, Long regiaoId) {
        // Cria o ponto de foco usando a factory
        PontoFoco pontoFoco = PontoFocoFactory.criarPontoFocoCompleto(
                latitude, longitude, intensidade, areaEstimada, descricao);
        
        // Associa à região, se informada
        if (regiaoId != null) {
            Optional<Regiao> regiaoOpt = regiaoRepository.findById(regiaoId);
            regiaoOpt.ifPresent(regiao -> {
                pontoFoco.setRegiao(regiao);
                // Recalcula o nível de risco da região após adicionar o ponto de foco
                regiao.recalcularNivelRisco();
                regiaoRepository.save(regiao);
            });
        }
        
        // Salva e retorna o ponto de foco criado
        return pontoFocoRepository.save(pontoFoco);
    }

    /**
     * Atualiza o status de um ponto de foco
     */
    @Transactional
    public Optional<PontoFoco> atualizarStatus(Long id, StatusPontoFoco novoStatus) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(id);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            pontoFoco.atualizarStatus(novoStatus);
            
            // Se o ponto de foco pertence a uma região, recalcula o nível de risco
            if (pontoFoco.getRegiao() != null) {
                Regiao regiao = pontoFoco.getRegiao();
                regiao.recalcularNivelRisco();
                regiaoRepository.save(regiao);
            }
            
            return Optional.of(pontoFocoRepository.save(pontoFoco));
        }
        
        return Optional.empty();
    }

    /**
     * Atualiza os detalhes de um ponto de foco
     */
    @Transactional
    public Optional<PontoFoco> atualizarDetalhesPontoFoco(Long id, Double intensidade, 
                                                      Double areaEstimada, String descricao) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(id);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            if (intensidade != null) {
                pontoFoco.setIntensidade(intensidade);
            }
            
            if (areaEstimada != null) {
                pontoFoco.setAreaEstimadaMetrosQuadrados(areaEstimada);
            }
            
            if (descricao != null) {
                pontoFoco.setDescricao(descricao);
            }
            
            pontoFoco.setDataAtualizacao(LocalDateTime.now());
            
            return Optional.of(pontoFocoRepository.save(pontoFoco));
        }
        
        return Optional.empty();
    }

    /**
     * Remove um ponto de foco
     */
    @Transactional
    public boolean removerPontoFoco(Long id) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(id);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            // Se o ponto de foco pertence a uma região, recalcula o nível de risco após remover
            Regiao regiao = pontoFoco.getRegiao();
            
            pontoFocoRepository.delete(pontoFoco);
            
            if (regiao != null) {
                regiao.recalcularNivelRisco();
                regiaoRepository.save(regiao);
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * Lista pontos de foco por proximidade geográfica
     */
    public List<PontoFoco> listarPorProximidade(Double latitude, Double longitude, Double raioGraus) {
        return pontoFocoRepository.findByProximidade(latitude, longitude, raioGraus);
    }

    /**
     * Lista pontos de foco por intensidade mínima em ordem descendente
     */
    public List<PontoFoco> listarPorIntensidade(Double intensidadeMinima) {
        return pontoFocoRepository.findByIntensidadeGreaterThanOrderByIntensidadeDesc(intensidadeMinima);
    }
}
