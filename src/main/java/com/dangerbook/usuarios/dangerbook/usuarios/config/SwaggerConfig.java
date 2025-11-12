package com.dangerbook.usuarios.dangerbook.usuarios.config;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Usuarios de DangerBook")
                        .version("1.0")
                        .description("Documentacion de la API de DangerBook para la sección de Usuarios.").contact(new Contact()
                                .name("Dangerbook")
                                .email("dangerbook@example.com")));
    }
}
