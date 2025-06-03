package br.com.fiap.queimadas.service;

import br.com.fiap.queimadas.domain.entity.AcaoCombate;
import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import br.com.fiap.queimadas.repository.AcaoCombateRepository;
import br.com.fiap.queimadas.repository.PontoFocoRepository;
import br.com.fiap.queimadas.service.factory.AcaoCombateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de ações de combate a incêndios
 */
@Service
public class AcaoCombateService {

    private final AcaoCombateRepository acaoCombateRepository;
    private final PontoFocoRepository pontoFocoRepository;

    @Autowired
    public AcaoCombateService(AcaoCombateRepository acaoCombateRepository,
                             PontoFocoRepository pontoFocoRepository) {
        this.acaoCombateRepository = acaoCombateRepository;
        this.pontoFocoRepository = pontoFocoRepository;
    }

    /**
     * Lista todas as ações de combate
     */
    public List<AcaoCombate> listarTodas() {
        return acaoCombateRepository.findAll();
    }

    /**
     * Busca uma ação de combate pelo ID
     */
    public Optional<AcaoCombate> buscarPorId(Long id) {
        return acaoCombateRepository.findById(id);
    }

    /**
     * Lista ações de combate por ponto de foco
     */
    public List<AcaoCombate> listarPorPontoFoco(Long pontoFocoId) {
        return acaoCombateRepository.findByPontoFocoId(pontoFocoId);
    }

    /**
     * Lista ações de combate em andamento
     */
    public List<AcaoCombate> listarEmAndamento() {
        return acaoCombateRepository.findByDataFimIsNull();
    }

    /**
     * Lista ações de combate por tipo
     */
    public List<AcaoCombate> listarPorTipo(String tipoAcao) {
        return acaoCombateRepository.findByTipoAcaoContainingIgnoreCase(tipoAcao);
    }

    /**
     * Lista ações de combate por região
     */
    public List<AcaoCombate> listarPorRegiao(Long regiaoId) {
        return acaoCombateRepository.findByRegiaoId(regiaoId);
    }

    /**
     * Inicia uma ação de combate terrestre para um ponto de foco
     */
    @Transactional
    public Optional<AcaoCombate> iniciarCombateTerrestre(Long pontoFocoId, String descricao, String responsavel) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(pontoFocoId);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            // Muda o status do ponto de foco para EM_COMBATE
            pontoFoco.atualizarStatus(StatusPontoFoco.EM_COMBATE);
            pontoFocoRepository.save(pontoFoco);
            
            // Cria a ação de combate usando a factory
            AcaoCombate acaoCombate = AcaoCombateFactory.criarAcaoCombateTerrestre(
                    pontoFoco, descricao, responsavel);
            
            return Optional.of(acaoCombateRepository.save(acaoCombate));
        }
        
        return Optional.empty();
    }

    /**
     * Inicia uma ação de combate aéreo para um ponto de foco
     */
    @Transactional
    public Optional<AcaoCombate> iniciarCombateAereo(Long pontoFocoId, String descricao, String responsavel) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(pontoFocoId);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            // Muda o status do ponto de foco para EM_COMBATE
            pontoFoco.atualizarStatus(StatusPontoFoco.EM_COMBATE);
            pontoFocoRepository.save(pontoFoco);
            
            // Cria a ação de combate usando a factory
            AcaoCombate acaoCombate = AcaoCombateFactory.criarAcaoCombateAereo(
                    pontoFoco, descricao, responsavel);
            
            return Optional.of(acaoCombateRepository.save(acaoCombate));
        }
        
        return Optional.empty();
    }

    /**
     * Inicia uma ação de monitoramento para um ponto de foco
     */
    @Transactional
    public Optional<AcaoCombate> iniciarMonitoramento(Long pontoFocoId, String descricao, String responsavel) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(pontoFocoId);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            // Muda o status do ponto de foco para MONITORAMENTO
            pontoFoco.atualizarStatus(StatusPontoFoco.MONITORAMENTO);
            pontoFocoRepository.save(pontoFoco);
            
            // Cria a ação de combate usando a factory
            AcaoCombate acaoCombate = AcaoCombateFactory.criarAcaoMonitoramento(
                    pontoFoco, descricao, responsavel);
            
            return Optional.of(acaoCombateRepository.save(acaoCombate));
        }
        
        return Optional.empty();
    }

    /**
     * Inicia uma ação personalizada para um ponto de foco
     */
    @Transactional
    public Optional<AcaoCombate> iniciarAcaoPersonalizada(Long pontoFocoId, String tipoAcao, 
                                                      String descricao, String responsavel, 
                                                      String recursosUtilizados, 
                                                      StatusPontoFoco statusPontoFoco) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoRepository.findById(pontoFocoId);
        
        if (pontoFocoOpt.isPresent()) {
            PontoFoco pontoFoco = pontoFocoOpt.get();
            
            // Muda o status do ponto de foco conforme especificado
            pontoFoco.atualizarStatus(statusPontoFoco);
            pontoFocoRepository.save(pontoFoco);
            
            // Cria a ação de combate usando a factory
            AcaoCombate acaoCombate = AcaoCombateFactory.criarAcaoPersonalizada(
                    pontoFoco, tipoAcao, descricao, responsavel, recursosUtilizados);
            
            return Optional.of(acaoCombateRepository.save(acaoCombate));
        }
        
        return Optional.empty();
    }

    /**
     * Conclui uma ação de combate e atualiza o status do ponto de foco
     */
    @Transactional
    public Optional<AcaoCombate> concluirAcao(Long acaoId, String resultado, StatusPontoFoco novoStatusPontoFoco) {
        Optional<AcaoCombate> acaoOpt = acaoCombateRepository.findById(acaoId);
        
        if (acaoOpt.isPresent() && acaoOpt.get().isEmAndamento()) {
            AcaoCombate acao = acaoOpt.get();
            
            // Conclui a ação
            acao.concluirAcao(resultado);
            
            // Atualiza o status do ponto de foco
            PontoFoco pontoFoco = acao.getPontoFoco();
            pontoFoco.atualizarStatus(novoStatusPontoFoco);
            pontoFocoRepository.save(pontoFoco);
            
            return Optional.of(acaoCombateRepository.save(acao));
        }
        
        return Optional.empty();
    }

    /**
     * Atualiza os detalhes de uma ação de combate em andamento
     */
    @Transactional
    public Optional<AcaoCombate> atualizarAcao(Long id, String descricao, String recursosUtilizados) {
        Optional<AcaoCombate> acaoOpt = acaoCombateRepository.findById(id);
        
        if (acaoOpt.isPresent() && acaoOpt.get().isEmAndamento()) {
            AcaoCombate acao = acaoOpt.get();
            
            if (descricao != null && !descricao.isEmpty()) {
                acao.setDescricao(descricao);
            }
            
            if (recursosUtilizados != null && !recursosUtilizados.isEmpty()) {
                acao.setRecursosUtilizados(recursosUtilizados);
            }
            
            return Optional.of(acaoCombateRepository.save(acao));
        }
        
        return Optional.empty();
    }

    /**
     * Remove uma ação de combate
     */
    @Transactional
    public boolean removerAcao(Long id) {
        if (acaoCombateRepository.existsById(id)) {
            acaoCombateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Lista ações de combate concluídas em um período
     */
    public List<AcaoCombate> listarConcluidasNoPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return acaoCombateRepository.findByDataFimBetween(inicio, fim);
    }
}
