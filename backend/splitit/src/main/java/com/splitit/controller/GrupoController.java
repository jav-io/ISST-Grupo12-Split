package com.splitit.controller;


import com.splitit.dto.GrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    // Endpoint para crear un grupo, recibiendo el DTO
    @PostMapping
    public ResponseEntity<Grupo> crearGrupo(@Valid @RequestBody GrupoDTO grupoDTO) {
        Grupo nuevoGrupo = grupoService.crearGrupo(grupoDTO);
        return ResponseEntity.ok(nuevoGrupo);
    }

    // Obtener todos los grupos
    @GetMapping
    public ResponseEntity<List<Grupo>> obtenerTodos() {
        List<Grupo> grupos = grupoService.obtenerTodos();
        return ResponseEntity.ok(grupos);
    }

    // Buscar grupo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarPorId(id);
        return ResponseEntity.ok(grupo);
    }
}
