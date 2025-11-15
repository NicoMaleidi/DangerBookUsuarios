package com.dangerbook.usuarios.dangerbook.usuarios.Service;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.UsuarioRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setNombre("Cristofano");
        usuario.setApellido("Butarecchi");
        usuario.setEmail("cristofano@example.com");
        usuario.setTelefono("987654321");
        usuario.setContrasena("12345");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setId_rol(1);
        usuario.setId_estado(1);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cristofano", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id);
        usuario.setNombre("Matías");
        usuario.setEmail("matias@example.com");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(id);

        assertNotNull(resultado);
        assertEquals("Matías", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByIdNoEncontrado() {
        Integer id = 99;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.findById(id);
        });

        assertEquals("Usuario no encontrado", excepcion.getMessage());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setNombre("Gastón");
        usuario.setEmail("gaston@example.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        assertNotNull(resultado);
        assertEquals("Gastón", resultado.getNombre());
        assertEquals("gaston@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
