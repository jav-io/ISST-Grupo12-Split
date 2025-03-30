package com.splitit.controller;

import com.splitit.model.Deuda;
import com.splitit.service.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    // Registrar una nueva deuda
    @PostMapping
    public ResponseEntity<Deuda> crearDeuda(@RequestBody Deuda deuda) {
        Deuda nuevaDeuda = deudaService.crearDeuda(deuda);
        return ResponseEntity.ok(nuevaDeuda);
    }

    // Obtener todas las deudas
    @GetMapping
    public ResponseEntity<List<Deuda>> obtenerTodos() {
        List<Deuda> deudas = deudaService.obtenerTodos();
        return ResponseEntity.ok(deudas);
    }

    // Buscar deuda por ID
    @GetMapping("/{id}")
    public ResponseEntity<Deuda> buscarPorId(@PathVariable Long id) {
        Deuda deuda = deudaService.buscarPorId(id);
        return ResponseEntity.ok(deuda);
    }
}
