package com.dangerbook.usuarios.dangerbook.usuarios.config;

import com.dangerbook.usuarios.dangerbook.usuarios.model.Estado;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Rol;
import com.dangerbook.usuarios.dangerbook.usuarios.model.Usuario;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.EstadoRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.RolRepository;
import com.dangerbook.usuarios.dangerbook.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EstadoRepository estadoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) return;

        Rol adminRole = rolRepository.save(new Rol(null, "Admin"));
        Rol barberRole = rolRepository.save(new Rol(null, "Barbero"));
        Rol clientRole = rolRepository.save(new Rol(null, "Cliente"));

        // Estado
        Estado activo = estadoRepository.save(new Estado(null, "Activo"));

        // ADMIN
        usuarioRepository.save(Usuario.builder()
                .nombre("Administrador")
                .apellido("General")
                .email("admin@danger.cl")
                .telefono("900000001")
                .contrasena(passwordEncoder.encode("admin123"))
                .fechaRegistro(LocalDateTime.now())
                .id_rol(adminRole.getId_rol())
                .id_estado(activo.getId_estado())
                .build()
        );

        // BARBEROS: Steve y Martin
        usuarioRepository.save(Usuario.builder()
                .nombre("Steve")
                .apellido("Lazaro")
                .email("steve@danger.cl")
                .telefono("900000002")
                .contrasena(passwordEncoder.encode("barber123"))
                .fechaRegistro(LocalDateTime.now())
                .id_rol(barberRole.getId_rol())
                .id_estado(activo.getId_estado())
                .build()
        );

        usuarioRepository.save(Usuario.builder()
                .nombre("Martin")
                .apellido("Svideski")
                .email("martin@danger.cl")
                .telefono("900000003")
                .contrasena(passwordEncoder.encode("barber123"))
                .fechaRegistro(LocalDateTime.now())
                .id_rol(barberRole.getId_rol())
                .id_estado(activo.getId_estado())
                .build()
        );

        // CLIENTES (faker)
        Faker faker = new Faker(new Locale("es", "CL"));
        for (int i = 0; i < 10; i++) {
            usuarioRepository.save(Usuario.builder()
                    .nombre(faker.name().firstName())
                    .apellido(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .telefono(faker.phoneNumber().cellPhone())
                    .contrasena(passwordEncoder.encode("password123"))
                    .fechaRegistro(LocalDateTime.now())
                    .id_rol(clientRole.getId_rol())
                    .id_estado(activo.getId_estado())
                    .build()
            );
        }

        System.out.println(" Usuarios creados: " + usuarioRepository.count());
    }
}
