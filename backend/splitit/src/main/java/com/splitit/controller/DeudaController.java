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

    @GetMapping
    public ResponseEntity<List<Deuda>> obtenerTodas() {
        return ResponseEntity.ok(deudaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deuda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(deudaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Deuda> crearDeuda(@RequestBody Deuda deuda) {
        return ResponseEntity.ok(deudaService.crearDeuda(deuda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDeuda(@PathVariable Long id) {
        deudaService.eliminarDeuda(id);
        return ResponseEntity.noContent().build();
    }
}
