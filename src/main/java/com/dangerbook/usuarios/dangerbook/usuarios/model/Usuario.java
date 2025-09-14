package com.dangerbook.usuarios.dangerbook.usuarios.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario" )
    private Long id_usuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    private String apellido;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "contrasena", nullable = false, length = 250)
    private String contrsaena;

    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    @Column(name = "fechaRegistro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "fotoPerfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;
}
