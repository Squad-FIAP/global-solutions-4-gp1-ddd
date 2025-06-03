package br.com.fiap.queimadas.controller;

import br.com.fiap.queimadas.domain.entity.AcaoCombate;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import br.com.fiap.queimadas.service.AcaoCombateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tagsw.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar ações de combate a incêndios
 */
@Tag(name = "Ações de Combate", description = "Operações relacionadas às ações de combate a incêndios")
@RestController
@RequestMapping("/api/acoes-combate")
public class AcaoCombateController {

    private final AcaoCombateService acaoCombateService;

    @Autowired
    public AcaoCombateController(AcaoCombateService acaoCombateService) {
        this.acaoCombateService = acaoCombateService;
    }

    /**
     * Lista todas as ações de combate
     */
    @GetMapping
    public ResponseEntity<List<AcaoCombate>> listarTodas() {
        return ResponseEntity.ok(acaoCombateService.listarTodas());
    }

    /**
     * Busca uma ação de combate pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcaoCombate> buscarPorId(@PathVariable Long id) {
        Optional<AcaoCombate> acaoOpt = acaoCombateService.buscarPorId(id);
        return acaoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista ações de combate por ponto de foco
     */
    @GetMapping("/ponto-foco/{pontoFocoId}")
    public ResponseEntity<List<AcaoCombate>> listarPorPontoFoco(@PathVariable Long pontoFocoId) {
        return ResponseEntity.ok(acaoCombateService.listarPorPontoFoco(pontoFocoId));
    }

    /**
     * Lista ações de combate em andamento
     */
    @GetMapping("/em-andamento")
    public ResponseEntity<List<AcaoCombate>> listarEmAndamento() {
        return ResponseEntity.ok(acaoCombateService.listarEmAndamento());
    }

    /**
     * Lista ações de combate por tipo
     */
    @GetMapping("/tipo")
    public ResponseEntity<List<AcaoCombate>> listarPorTipo(@RequestParam String tipoAcao) {
        return ResponseEntity.ok(acaoCombateService.listarPorTipo(tipoAcao));
    }

    /**
     * Lista ações de combate por região
     */
    @GetMapping("/regiao/{regiaoId}")
    public ResponseEntity<List<AcaoCombate>> listarPorRegiao(@PathVariable Long regiaoId) {
        return ResponseEntity.ok(acaoCombateService.listarPorRegiao(regiaoId));
    }

    /**
     * Inicia uma ação de combate terrestre
     */
    @PostMapping("/terrestre/{pontoFocoId}")
    public ResponseEntity<AcaoCombate> iniciarCombateTerrestre(
            @PathVariable Long pontoFocoId,
            @RequestParam String descricao,
            @RequestParam String responsavel) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.iniciarCombateTerrestre(pontoFocoId, descricao, responsavel);
        return acaoOpt.map(acao -> ResponseEntity.status(HttpStatus.CREATED).body(acao))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Inicia uma ação de combate aéreo
     */
    @PostMapping("/aereo/{pontoFocoId}")
    public ResponseEntity<AcaoCombate> iniciarCombateAereo(
            @PathVariable Long pontoFocoId,
            @RequestParam String descricao,
            @RequestParam String responsavel) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.iniciarCombateAereo(pontoFocoId, descricao, responsavel);
        return acaoOpt.map(acao -> ResponseEntity.status(HttpStatus.CREATED).body(acao))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Inicia uma ação de monitoramento
     */
    @PostMapping("/monitoramento/{pontoFocoId}")
    public ResponseEntity<AcaoCombate> iniciarMonitoramento(
            @PathVariable Long pontoFocoId,
            @RequestParam String descricao,
            @RequestParam String responsavel) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.iniciarMonitoramento(pontoFocoId, descricao, responsavel);
        return acaoOpt.map(acao -> ResponseEntity.status(HttpStatus.CREATED).body(acao))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Inicia uma ação personalizada
     */
    @PostMapping("/personalizada/{pontoFocoId}")
    public ResponseEntity<AcaoCombate> iniciarAcaoPersonalizada(
            @PathVariable Long pontoFocoId,
            @RequestParam String tipoAcao,
            @RequestParam String descricao,
            @RequestParam String responsavel,
            @RequestParam String recursosUtilizados,
            @RequestParam StatusPontoFoco statusPontoFoco) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.iniciarAcaoPersonalizada(
                pontoFocoId, tipoAcao, descricao, responsavel, recursosUtilizados, statusPontoFoco);
        
        return acaoOpt.map(acao -> ResponseEntity.status(HttpStatus.CREATED).body(acao))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Conclui uma ação de combate
     */
    @PostMapping("/{acaoId}/concluir")
    public ResponseEntity<AcaoCombate> concluirAcao(
            @PathVariable Long acaoId,
            @RequestParam String resultado,
            @RequestParam StatusPontoFoco novoStatusPontoFoco) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.concluirAcao(acaoId, resultado, novoStatusPontoFoco);
        return acaoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os detalhes de uma ação em andamento
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AcaoCombate> atualizarAcao(
            @PathVariable Long id,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String recursosUtilizados) {
        
        Optional<AcaoCombate> acaoOpt = acaoCombateService.atualizarAcao(id, descricao, recursosUtilizados);
        return acaoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove uma ação de combate
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAcao(@PathVariable Long id) {
        boolean removido = acaoCombateService.removerAcao(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Lista ações concluídas em um período
     */
    @GetMapping("/concluidas")
    public ResponseEntity<List<AcaoCombate>> listarConcluidasNoPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        return ResponseEntity.ok(acaoCombateService.listarConcluidasNoPeriodo(inicio, fim));
    }
}
