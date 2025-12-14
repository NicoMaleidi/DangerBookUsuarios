package com.dangerbook.usuarios.dangerbook.usuarios.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa a un usuario del sistema DangerBook")
public class Usuario {
    @Schema(description = "ID único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario" )
    private Integer id_usuario;

    @Schema(description = "Nombre del usuario", example = "Steve")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Lazaro")
    @Column(name = "apellido", length = 100)
    private String apellido;

    @Schema(description = "Correo electrónico", example = "steve@dangerbook.cl")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Schema(description = "Contraseña encriptada con BCrypt", example = "$2a$10$...")
    @Column(name = "contrasena", nullable = false, length = 250)
    private String contrasena;

    @Schema(description = "Fecha y hora de registro del usuario", example = "2024-06-15T10:15:30")
    @Column(name = "fechaRegistro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @Schema(description = "Foto de perfil del usuario en formato byte array")
    @Lob
    @Column(name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;

    //IDs de otras entidades
    @Schema(description = "Rol del usuario (1=Admin, 2=Barbero, 3=Cliente)", example = "3")
    @Column(name = "id_rol")
    private Integer id_rol;

    @Schema(description = "Estado del usuario (1=Activo, 2=Inactivo)", example = "1")
    @Column(name = "id_estado")
    private Integer id_estado;
}
