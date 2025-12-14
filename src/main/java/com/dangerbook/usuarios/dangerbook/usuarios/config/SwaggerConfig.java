package com.dangerbook.usuarios.dangerbook.usuarios.config;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usuariosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Usuarios - DangerBook")
                        .version("1.0.0")
                        .description("""
                                API encargada de la gestión de usuarios del sistema DangerBook.

                                Funcionalidades:
                                - Registro de usuarios
                                - Autenticación (login)
                                - Gestión de roles (Cliente, Barbero, Admin)
                                - Actualización de perfil y contraseña
                                - Subida de foto de perfil
                                """)
                        .contact(new Contact()
                                .name("Equipo DangerBook")
                                .email("contacto@dangerbook.cl")
                        )
                );
    }
}
