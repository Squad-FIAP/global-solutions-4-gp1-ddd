package br.com.fiap.queimadas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do Swagger/OpenAPI para documentação da API
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8082}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:" + serverPort)
                                .description("Servidor local")
                ))
                .info(new Info()
                        .title("API de Monitoramento e Combate a Queimadas")
                        .description("API REST para gestão de pontos de foco de incêndio, " +
                                "regiões monitoradas e ações de combate a queimadas")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Gabriel Mediotti Marques & Jó Sales")
                                .email("contato@fiap.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
