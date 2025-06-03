package br.com.fiap.queimadas.controller;

import br.com.fiap.queimadas.domain.entity.Regiao;
import br.com.fiap.queimadas.service.RegiaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar regiões monitoradas
 */
@Tag(name = "Regiões", description = "Operações relacionadas a regiões monitoradas")
@RestController
@RequestMapping("/api/regioes")
public class RegiaoController {

    private final RegiaoService regiaoService;

    @Autowired
    public RegiaoController(RegiaoService regiaoService) {
        this.regiaoService = regiaoService;
    }

    /**
     * Lista todas as regiões
     */
    @GetMapping
    public ResponseEntity<List<Regiao>> listarTodas() {
        return ResponseEntity.ok(regiaoService.listarTodas());
    }

    /**
     * Busca uma região pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Regiao> buscarPorId(@PathVariable Long id) {
        Optional<Regiao> regiaoOpt = regiaoService.buscarPorId(id);
        return regiaoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista regiões por nome
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Regiao>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(regiaoService.buscarPorNome(nome));
    }

    /**
     * Lista regiões por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Regiao>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(regiaoService.buscarPorTipo(tipo));
    }

    /**
     * Lista regiões por nível de risco
     */
    @GetMapping("/risco/{nivelMinimo}")
    public ResponseEntity<List<Regiao>> listarPorNivelRisco(@PathVariable Integer nivelMinimo) {
        return ResponseEntity.ok(regiaoService.listarPorNivelRiscoMinimo(nivelMinimo));
    }

    /**
     * Lista regiões ordenadas por pontos de foco ativos
     */
    @GetMapping("/com-focos")
    public ResponseEntity<List<Regiao>> listarPorPontosFocoAtivos() {
        return ResponseEntity.ok(regiaoService.listarPorPontosFocoAtivos());
    }

    /**
     * Lista regiões sem pontos de foco ativos
     */
    @GetMapping("/sem-focos")
    public ResponseEntity<List<Regiao>> listarSemPontosFoco() {
        return ResponseEntity.ok(regiaoService.listarRegioesSemPontosFocoAtivos());
    }

    /**
     * Registra uma nova região
     */
    @PostMapping
    public ResponseEntity<Regiao> registrarRegiao(@Valid @RequestBody Regiao regiao) {
        Regiao novaRegiao = regiaoService.registrarRegiao(regiao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRegiao);
    }

    /**
     * Atualiza uma região
     */
    @PutMapping("/{id}")
    public ResponseEntity<Regiao> atualizarRegiao(
            @PathVariable Long id,
            @Valid @RequestBody Regiao regiao) {
        
        Optional<Regiao> regiaoAtualizadaOpt = regiaoService.atualizarRegiao(id, regiao);
        return regiaoAtualizadaOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Recalcula o nível de risco de uma região
     */
    @PostMapping("/{id}/recalcular-risco")
    public ResponseEntity<Regiao> recalcularNivelRisco(@PathVariable Long id) {
        Optional<Regiao> regiaoOpt = regiaoService.recalcularNivelRisco(id);
        return regiaoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove uma região
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRegiao(@PathVariable Long id) {
        boolean removido = regiaoService.removerRegiao(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
