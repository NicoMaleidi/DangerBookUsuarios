package com.dangerbook.usuarios.dangerbook.usuarios.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer id_estado;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
