package com.dangerbook.usuarios.dangerbook.usuarios.Controller;

import com.dangerbook.usuarios.dangerbook.usuarios.controller.UsuarioController;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setNombre("Nicolas");
        usuario.setApellido("Vallejos");
        usuario.setEmail("nicolasito@example.com");
        usuario.setTelefono("987654321");
        usuario.setContrasena("12345");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setId_rol(1);
        usuario.setId_estado(1);
    }

    @Test
    void testListarUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Nicolas"))
                .andExpect(jsonPath("$[0].email").value("nicolasito@example.com"));
    }

    @Test
    void testListarUsuariosVacio() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(usuarioService.findById(1)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nicolas"))
                .andExpect(jsonPath("$.email").value("nicolasito@example.com"));
    }

    @Test
    void testBuscarPorIdNoEncontrado() throws Exception {
        when(usuarioService.findById(99)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearUsuario() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Cristofano"))
                .andExpect(jsonPath("$.email").value("cristofano@example.com"));
    }
}
