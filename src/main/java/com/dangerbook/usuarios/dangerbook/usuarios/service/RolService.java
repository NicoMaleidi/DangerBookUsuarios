package com.dangerbook.usuarios.dangerbook.usuarios.service;


import com.dangerbook.usuarios.dangerbook.usuarios.model.Rol;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
}
