package com.dangerbook.usuarios.dangerbook.usuarios.repository;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
