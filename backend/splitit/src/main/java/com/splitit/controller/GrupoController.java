package com.splitit.controller;

import com.splitit.model.Grupo;
import com.splitit.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    // Crear un nuevo grupo
    @PostMapping
    public ResponseEntity<Grupo> crearGrupo(@RequestBody Grupo grupo) {
        Grupo nuevoGrupo = grupoService.crearGrupo(grupo);
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
