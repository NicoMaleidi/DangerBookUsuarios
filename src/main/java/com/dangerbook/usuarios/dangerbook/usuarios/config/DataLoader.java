package com.dangerbook.usuarios.dangerbook.usuarios.config;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Estado;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Rol;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.EstadoRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.RolRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.UsuarioRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.service.EstadoService;
import com.dangerbook.usuarios.dangerbook.usuarios.service.RolService;
import com.dangerbook.usuarios.dangerbook.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioService usuarioService;
    private final EstadoRepository estadoRepository;

    @Override
    public void run(String... args) {
        // Evita duplicar datos si ya existen usuarios
        if (usuarioRepository.count() > 0) return;

        // Crear datos base si las tablas están vacías
        Rol rolUsuario = rolRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Rol nuevo = new Rol();
                    nuevo.setNombre("Cliente");
                    return rolRepository.save(nuevo);
                });

        Estado estadoActivo = estadoRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Estado nuevo = new Estado();
                    nuevo.setNombre("Activo");
                    return estadoRepository.save(nuevo);
                });

        // Faker para generar datos realistas
        Faker faker = new Faker(new Locale("es-CL"), new Random(123));

        // Generar usuarios de prueba
        for (int i = 0; i < 15; i++) {
            Usuario u = new Usuario();
            u.setNombre(faker.name().firstName());
            u.setApellido(faker.name().lastName());
            u.setEmail(faker.internet().emailAddress());
            u.setTelefono(faker.phoneNumber().cellPhone());
            u.setContrsaena("password123"); // en desarrollo no se encripta, solo test
            u.setFechaRegistro(LocalDateTime.now());
            u.setFotoPerfil(null);
            u.setId_rol(rolUsuario.getId_rol());
            u.setId_estado(estadoActivo.getId_estado());

            // Usamos el servicio para aplicar lógica interna (si la hubiera)
            usuarioService.save(u);
        }

        System.out.println(" Usuarios de prueba generados correctamente (" + usuarioRepository.count() + " registros).");
    }
}
