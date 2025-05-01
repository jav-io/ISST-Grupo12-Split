package com.splitit.controller;

import com.splitit.model.Usuario;
import com.splitit.service.UsuarioService;
import com.splitit.DTO.PasswordResetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Map;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

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

    // Cambiar contraseña mediante email
    @PostMapping("/recuperar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody PasswordResetDTO dto) {
        try {
            usuarioService.cambiarPasswordPorEmail(dto.getEmail(), dto.getNewPassword());
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/solicitar-recuperacion")
    public ResponseEntity<String> solicitarRecuperacion(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        if (!usuarioService.verificarEmailExistente(email)) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo no está registrado.");
        }

        // Aquí iría lógica de envío de email, pero por ahora simulamos
        return ResponseEntity.ok("Correo de recuperación enviado.");
    }
}
