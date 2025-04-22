package com.splitit.controller;

import com.splitit.model.Usuario;
import com.splitit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.splitit.dto.PasswordResetDTO;


import java.util.List;

/**
 * Controlador REST para gestionar usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear nuevo usuario (registro)
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody PasswordResetDTO dto) {
        usuarioService.cambiarPasswordPorEmail(dto.getEmail(), dto.getNuevaPassword());
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

}
