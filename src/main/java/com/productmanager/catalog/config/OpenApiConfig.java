package com.productmanager.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI productManagerOpenApi() {
        return new OpenAPI().info(new Info()
                .title("API de gestión de productos")
                .version("1.0.0")
                .description(
                        "API REST para administrar un catálogo de productos almacenado temporalmente en memoria. Incluye consulta, creación, actualización de existencias y eliminación."));
    }
}
