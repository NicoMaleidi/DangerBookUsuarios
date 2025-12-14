package com.dangerbook.usuarios.dangerbook.usuarios.config;
import lombok.Data;

@Data
public class CambiarContrasena {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
