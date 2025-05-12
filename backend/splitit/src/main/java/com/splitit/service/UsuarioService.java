package com.splitit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitit.model.Usuario;
import com.splitit.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // Obtener todos
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    // Autenticación (este método ya no se usa directamente, Spring Security se encarga)
    @Deprecated
    public Usuario autenticarUsuario(String email, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("No existe un usuario con ese correo.");
        }

        Usuario usuario = optionalUsuario.get();
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return usuario;
    }

    // Recuperación de contraseña - cambio directo
    public void cambiarPasswordPorEmail(String email, String nuevaPassword) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("No existe un usuario con ese correo.");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
    }

    // Solo buscar sin lanzar error
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    // Método para migrar contraseñas existentes a formato hasheado
    public void migrarContrasenas() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            // Verificamos si la contraseña ya está hasheada
            if (!usuario.getPassword().startsWith("$2a$")) {
                String passwordHasheada = passwordEncoder.encode(usuario.getPassword());
                usuario.setPassword(passwordHasheada);
                usuarioRepository.save(usuario);
            }
        }
    }

public Usuario buscarOCrearPorEmail(String email, String nombre) {
    Optional<Usuario> existente = usuarioRepository.findByEmail(email);
    if (existente.isPresent()) {
        return existente.get();
    }

    Usuario usuarioFantasma = new Usuario();
    usuarioFantasma.setEmail(email);
    usuarioFantasma.setNombre(nombre);
    usuarioFantasma.setPassword(passwordEncoder.encode("temporal")); // o algo aleatorio
    usuarioFantasma.setRegistrado(false);
    return usuarioRepository.save(usuarioFantasma);
}
public Optional<Usuario> buscarPorNombre(String nombre) {
    return usuarioRepository.findByNombre(nombre);
}

    
}
