package com.dangerbook.usuarios.dangerbook.usuarios.service;


import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findByEmailAndPassword(String email, String contrasena) {
    return usuarioRepository.findByEmailAndContrasena(email, contrasena)
            .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
}
}
