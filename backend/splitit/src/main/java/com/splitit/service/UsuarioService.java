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

    public Usuario crearUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("El email ya está en uso.");
        }

        // SIN ENCRIPTAR LA CONTRASEÑA
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        System.out.println("[DEBUG] Email: " + email);
        System.out.println("[DEBUG] Password introducida: [" + password + "]");
        System.out.println("[DEBUG] Password almacenada: [" + usuario.getPassword() + "]");
    
        if (!usuario.getPassword().trim().equals(password.trim())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
    
        return usuario;
    }
    
    public void cambiarPasswordPorEmail(String email, String nuevaPassword) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
        throw new RuntimeException("No se encontró un usuario con el correo: " + email);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
    }



}
