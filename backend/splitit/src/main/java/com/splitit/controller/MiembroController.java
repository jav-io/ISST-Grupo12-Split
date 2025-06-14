package com.splitit.controller;

import jakarta.validation.Valid;
import com.splitit.dto.InvitacionDTO;
import com.splitit.model.Miembro;
import com.splitit.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    // ✅ CORREGIDO: Invitar por email (ya no por idUsuario)
    @PostMapping("/invitar")
    public ResponseEntity<Miembro> invitarMiembro(@Valid @RequestBody InvitacionDTO invitacionDTO) {
        Miembro nuevoMiembro = miembroService.invitarMiembroPorEmail(
                invitacionDTO.getIdGrupo(),
                invitacionDTO.getEmail() // <- nuevo campo en DTO
        );
        return ResponseEntity.ok(nuevoMiembro);
    }

    // Agregar un nuevo miembro a un grupo
    @PostMapping
    public ResponseEntity<Miembro> crearMiembro(@RequestBody Miembro miembro) {
        Miembro nuevoMiembro = miembroService.crearMiembro(miembro);
        return ResponseEntity.ok(nuevoMiembro);
    }

    // Obtener todos los miembros
    @GetMapping
    public ResponseEntity<List<Miembro>> obtenerTodos() {
        List<Miembro> miembros = miembroService.obtenerTodos();
        return ResponseEntity.ok(miembros);
    }

    // Buscar miembro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Miembro> buscarPorId(@PathVariable Long id) {
        Miembro miembro = miembroService.buscarPorId(id);
        return ResponseEntity.ok(miembro);
    }
}
