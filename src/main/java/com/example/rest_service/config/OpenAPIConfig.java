package com.example.rest_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Multi-Controller API")
                        .version("1.0")
                        .description("API Documentation for multiple controllers"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server"),
                        new Server()
                                .url("https://staging-api.example.com")
                                .description("Staging Server")
                ));
    }

    @Bean
    public GroupedOpenApi transactionControllerApi() {
        return GroupedOpenApi.builder()
                .group("Transaction API")
                .displayName("Transaction Management")
                .pathsToMatch("/transactions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userControllerApi() {
        return GroupedOpenApi.builder()
                .group("User API")
                .displayName("User Management")
                .pathsToMatch("/users/**")
                .build();
    }
}