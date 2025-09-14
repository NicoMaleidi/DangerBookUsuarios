package com.dangerbook.usuarios.dangerbook.usuarios.service;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Estado;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }
}
