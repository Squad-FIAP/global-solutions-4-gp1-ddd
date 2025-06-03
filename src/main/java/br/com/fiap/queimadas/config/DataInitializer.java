package br.com.fiap.queimadas.config;

import br.com.fiap.queimadas.domain.entity.PontoFoco;
import br.com.fiap.queimadas.domain.entity.Regiao;
import br.com.fiap.queimadas.domain.enums.StatusPontoFoco;
import br.com.fiap.queimadas.repository.PontoFocoRepository;
import br.com.fiap.queimadas.repository.RegiaoRepository;
import br.com.fiap.queimadas.service.AcaoCombateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuração para carregar dados iniciais no banco de dados
 * Apenas para ambiente de desenvolvimento e testes
 */
@Configuration
@Slf4j
@Profile({"dev", "test"}) // Executa apenas em ambientes de dev e test
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(@Autowired RegiaoRepository regiaoRepository,
                                     @Autowired PontoFocoRepository pontoFocoRepository,
                                     @Autowired AcaoCombateService acaoCombateService) {
        return args -> {
            log.info("Inicializando dados de exemplo...");
            
            // Criação das regiões
            Regiao amazoniaLegal = Regiao.builder()
                    .nome("Amazônia Legal")
                    .tipo("Floresta")
                    .areaMetrosQuadrados(5016136.0 * 1000000) // Aproximadamente 5 milhões de km²
                    .descricao("Área que engloba nove estados brasileiros pertencentes à Bacia Amazônica")
                    .nivelRisco(3)
                    .build();
            
            Regiao cerrado = Regiao.builder()
                    .nome("Cerrado Central")
                    .tipo("Savana")
                    .areaMetrosQuadrados(2000000.0 * 1000000) // Aproximadamente 2 milhões de km²
                    .descricao("Região de cerrado no Brasil central")
                    .nivelRisco(4)
                    .build();
            
            Regiao pantanal = Regiao.builder()
                    .nome("Pantanal")
                    .tipo("Área Úmida")
                    .areaMetrosQuadrados(150000.0 * 1000000) // Aproximadamente 150 mil km²
                    .descricao("Maior planície alagável do mundo")
                    .nivelRisco(2)
                    .build();
            
            List<Regiao> regioes = regiaoRepository.saveAll(Arrays.asList(amazoniaLegal, cerrado, pantanal));
            log.info("Regiões criadas: {}", regioes.size());
            
            // Criação dos pontos de foco
            PontoFoco pontoFoco1 = PontoFoco.builder()
                    .latitude(-3.4653)
                    .longitude(-62.2159)
                    .dataDeteccao(LocalDateTime.now().minusDays(2))
                    .intensidade(75.3)
                    .areaEstimadaMetrosQuadrados(15000.0)
                    .status(StatusPontoFoco.CONFIRMADO)
                    .descricao("Foco de incêndio de alta intensidade em área de floresta densa")
                    .dataAtualizacao(LocalDateTime.now().minusHours(12))
                    .regiao(amazoniaLegal)
                    .build();
            
            PontoFoco pontoFoco2 = PontoFoco.builder()
                    .latitude(-15.6761)
                    .longitude(-47.8613)
                    .dataDeteccao(LocalDateTime.now().minusDays(1))
                    .intensidade(45.8)
                    .areaEstimadaMetrosQuadrados(8000.0)
                    .status(StatusPontoFoco.EM_COMBATE)
                    .descricao("Foco de incêndio em área de cerrado próximo a área de preservação")
                    .dataAtualizacao(LocalDateTime.now().minusHours(6))
                    .regiao(cerrado)
                    .build();
            
            PontoFoco pontoFoco3 = PontoFoco.builder()
                    .latitude(-17.7086)
                    .longitude(-57.4261)
                    .dataDeteccao(LocalDateTime.now().minusDays(3))
                    .intensidade(25.2)
                    .areaEstimadaMetrosQuadrados(3000.0)
                    .status(StatusPontoFoco.MONITORAMENTO)
                    .descricao("Pequeno foco de incêndio em área de vegetação rasteira")
                    .dataAtualizacao(LocalDateTime.now().minusHours(24))
                    .regiao(pantanal)
                    .build();
            
            PontoFoco pontoFoco4 = PontoFoco.builder()
                    .latitude(-3.9842)
                    .longitude(-63.1427)
                    .dataDeteccao(LocalDateTime.now().minusHours(8))
                    .intensidade(90.1)
                    .areaEstimadaMetrosQuadrados(25000.0)
                    .status(StatusPontoFoco.NOVO)
                    .descricao("Novo foco de alta intensidade detectado por satélite")
                    .dataAtualizacao(LocalDateTime.now().minusHours(8))
                    .regiao(amazoniaLegal)
                    .build();
            
            List<PontoFoco> pontosFoco = pontoFocoRepository.saveAll(Arrays.asList(pontoFoco1, pontoFoco2, pontoFoco3, pontoFoco4));
            log.info("Pontos de foco criados: {}", pontosFoco.size());
            
            // Iniciar algumas ações de combate
            acaoCombateService.iniciarCombateTerrestre(pontoFoco2.getId(), 
                    "Combate terrestre com brigada do IBAMA", "Equipe PREVFOGO");
            
            acaoCombateService.iniciarMonitoramento(pontoFoco3.getId(),
                    "Monitoramento via drones e equipes de campo", "Defesa Civil");
            
            log.info("Dados iniciais carregados com sucesso!");
        };
    }
}
