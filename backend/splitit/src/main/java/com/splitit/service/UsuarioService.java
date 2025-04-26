package com.splitit.service;

import com.splitit.model.Usuario;
import com.splitit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }
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

    // Autenticación
    public Usuario autenticarUsuario(String email, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("No existe un usuario con ese correo.");
        }

        Usuario usuario = optionalUsuario.get();
        if (!usuario.getPassword().equals(password)) {
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
        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
    }

    // Solo buscar sin lanzar error
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

}
