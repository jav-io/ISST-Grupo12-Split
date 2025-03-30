package com.splitit.controller;

import com.splitit.model.Gasto;
import com.splitit.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    // Registrar un nuevo gasto
    @PostMapping
    public ResponseEntity<Gasto> crearGasto(@RequestBody Gasto gasto) {
        Gasto nuevoGasto = gastoService.crearGasto(gasto);
        return ResponseEntity.ok(nuevoGasto);
    }

    // Obtener todos los gastos
    @GetMapping
    public ResponseEntity<List<Gasto>> obtenerTodos() {
        List<Gasto> gastos = gastoService.obtenerTodos();
        return ResponseEntity.ok(gastos);
    }

    // Buscar gasto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Gasto> buscarPorId(@PathVariable Long id) {
        Gasto gasto = gastoService.buscarPorId(id);
        return ResponseEntity.ok(gasto);
    }
}
