package com.dangerbook.usuarios.dangerbook.usuarios.config;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos para actualizar la contraseña del usuario")
public class CambiarContrasena {
    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    @Schema(description = "Contraseña actual del usuario", example = "oldPassword123")
    private String oldPassword;
    
    @Schema(description = "Nueva contraseña del usuario", example = "newPassword456")
    private String newPassword;
}
