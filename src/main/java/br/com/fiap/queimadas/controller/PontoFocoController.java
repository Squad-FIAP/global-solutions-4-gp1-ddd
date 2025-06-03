package br.com.fiap.queimadas.controller;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import br.com.fiap.queimadas.service.PontoFocoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar pontos de foco de incêndio
 */
@Tag(name = "Pontos de Foco", description = "Operações relacionadas a pontos de foco de incêndio")
@RestController
@RequestMapping("/api/pontos-foco")
public class PontoFocoController {

    private final PontoFocoService pontoFocoService;

    @Autowired
    public PontoFocoController(PontoFocoService pontoFocoService) {
        this.pontoFocoService = pontoFocoService;
    }

    /**
     * Lista todos os pontos de foco
     */
    @GetMapping
    public ResponseEntity<List<PontoFoco>> listarTodos() {
        return ResponseEntity.ok(pontoFocoService.listarTodos());
    }

    /**
     * Busca um ponto de foco pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PontoFoco> buscarPorId(@PathVariable Long id) {
        Optional<PontoFoco> pontoFocoOpt = pontoFocoService.buscarPorId(id);
        return pontoFocoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista pontos de foco por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PontoFoco>> listarPorStatus(@PathVariable StatusPontoFoco status) {
        return ResponseEntity.ok(pontoFocoService.listarPorStatus(status));
    }

    /**
     * Lista pontos de foco ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<PontoFoco>> listarAtivos() {
        return ResponseEntity.ok(pontoFocoService.listarAtivos());
    }

    /**
     * Lista pontos de foco por região
     */
    @GetMapping("/regiao/{regiaoId}")
    public ResponseEntity<List<PontoFoco>> listarPorRegiao(@PathVariable Long regiaoId) {
        return ResponseEntity.ok(pontoFocoService.listarPorRegiao(regiaoId));
    }

    /**
     * Registra um novo ponto de foco
     */
    @Operation(
        summary = "Registra um novo ponto de foco", 
        description = "Cria um novo registro de ponto de foco com coordenadas geográficas e, opcionalmente, associa a uma região"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ponto de foco criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Região não encontrada")
    })
    @PostMapping
    public ResponseEntity<PontoFoco> registrarPontoFoco(
            @Parameter(description = "Latitude da localização") @RequestParam Double latitude,
            @Parameter(description = "Longitude da localização") @RequestParam Double longitude,
            @Parameter(description = "ID da região (opcional)") @RequestParam(required = false) Long regiaoId) {
        
        PontoFoco pontoFoco = pontoFocoService.registrarPontoFoco(latitude, longitude, regiaoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoFoco);
    }

    /**
     * Registra um ponto de foco completo
     */
    @PostMapping("/completo")
    public ResponseEntity<PontoFoco> registrarPontoFocoCompleto(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false) Double intensidade,
            @RequestParam(required = false) Double areaEstimada,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) Long regiaoId) {
        
        PontoFoco pontoFoco = pontoFocoService.registrarPontoFocoCompleto(
                latitude, longitude, intensidade, areaEstimada, descricao, regiaoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoFoco);
    }

    /**
     * Atualiza o status de um ponto de foco
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PontoFoco> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPontoFoco status) {
        
        Optional<PontoFoco> pontoFocoOpt = pontoFocoService.atualizarStatus(id, status);
        return pontoFocoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os detalhes de um ponto de foco
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PontoFoco> atualizarDetalhes(
            @PathVariable Long id,
            @RequestParam(required = false) Double intensidade,
            @RequestParam(required = false) Double areaEstimada,
            @RequestParam(required = false) String descricao) {
        
        Optional<PontoFoco> pontoFocoOpt = pontoFocoService.atualizarDetalhesPontoFoco(
                id, intensidade, areaEstimada, descricao);
        return pontoFocoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um ponto de foco
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPontoFoco(@PathVariable Long id) {
        boolean removido = pontoFocoService.removerPontoFoco(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Lista pontos de foco por proximidade geográfica
     */
    @GetMapping("/proximidade")
    public ResponseEntity<List<PontoFoco>> listarPorProximidade(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double raioGraus) {
        
        return ResponseEntity.ok(pontoFocoService.listarPorProximidade(latitude, longitude, raioGraus));
    }

    /**
     * Lista pontos de foco por intensidade mínima
     */
    @GetMapping("/intensidade")
    public ResponseEntity<List<PontoFoco>> listarPorIntensidade(
            @RequestParam Double intensidadeMinima) {
        
        return ResponseEntity.ok(pontoFocoService.listarPorIntensidade(intensidadeMinima));
    }
}
