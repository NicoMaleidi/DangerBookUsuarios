package com.dangerbook.usuarios.dangerbook.usuarios.service;


import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        // Encriptar contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    public Usuario findByEmailAndPassword(String email, String contrasena) {
    return usuarioRepository.findByEmailAndContrasena(email, contrasena)
            .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
    }


    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario login(String email, String contrasena) {
    return findByEmailAndPassword(email, contrasena);
    }

    @Transactional
    public void updatePhoto(Long id, byte[] photoBytes) {
    Usuario usuario = usuarioRepository.findById(id.intValue())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    usuario.setFotoPerfil(photoBytes);
    usuarioRepository.save(usuario);
    }

    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {

        Usuario usuario = usuarioRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 1. Validar contraseña actual
        if (!passwordEncoder.matches(oldPassword, usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        // 2. Encriptar nueva contraseña
        usuario.setContrasena(passwordEncoder.encode(newPassword));

        // 3. Guardar
        usuarioRepository.save(usuario);
    }
            
}
