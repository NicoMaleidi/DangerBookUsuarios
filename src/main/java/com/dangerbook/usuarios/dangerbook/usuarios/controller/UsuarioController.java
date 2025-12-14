package com.dangerbook.usuarios.dangerbook.usuarios.controller;

import com.dangerbook.usuarios.dangerbook.usuarios.config.CambiarContrasena;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Usuarios",
        description = "Endpoints para la gestión de usuarios del sistema"
)
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene la lista completa de usuarios registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen usuarios")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> lista = usuarioService.findAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Obtener usuario por ID
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene un usuario específico según su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar usuario
    @Operation(
            summary = "Registro de usuario",
            description = "Post un nuevo usuario al sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }

    // Login
    @Operation(
            summary = "Login de usuario",
            description = "Autentica un usuario usando email y contraseña (BCrypt)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
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

    @Operation(
            summary = "Actualizar foto de perfil",
            description = "Sube una imagen y la guarda en la base de datos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto actualizada"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
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

    @Operation(
            summary = "Actualizar contraseña",
            description = "Permite al usuario cambiar su contraseña"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contraseña actualizada"),
            @ApiResponse(responseCode = "400", description = "Contraseña actual incorrecta")
    })
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