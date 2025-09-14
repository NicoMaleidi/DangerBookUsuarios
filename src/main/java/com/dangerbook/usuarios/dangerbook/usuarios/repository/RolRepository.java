package com.dangerbook.usuarios.dangerbook.usuarios.repository;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
}
