package com.dangerbook.usuarios.dangerbook.usuarios.controller;

import com.dangerbook.usuarios.dangerbook.usuarios.config.CambiarContrasena;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> lista = usuarioService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar usuario
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String contrasena = body.get("contrasena");


    try {
        Usuario u = usuarioService.findByEmail(email);
        if (u == null || !passwordEncoder.matches(contrasena, u.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return ResponseEntity.ok(u);
    } catch (RuntimeException e) {
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }   
    }

    @PostMapping("/update-photo/{id}")
    public ResponseEntity<String> updatePhoto(
        @PathVariable Long id,
        @RequestParam("photo") MultipartFile photoFile) {

        try {
            usuarioService.updatePhoto(id, photoFile.getBytes());
            return ResponseEntity.ok("Foto actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar foto");
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(
        @RequestBody CambiarContrasena request) {

        try {
            usuarioService.updatePassword(
                    request.getUserId(),
                    request.getOldPassword(),
                    request.getNewPassword()
        );
        return ResponseEntity.ok().build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}