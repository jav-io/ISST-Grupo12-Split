package com.splitit.controller;

import com.splitit.model.Gasto;
import com.splitit.dto.GastoDTO;
import com.splitit.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    // Endpoint para crear un gasto a partir de GastoDTO
    @PostMapping
    public ResponseEntity<Gasto> crearGasto(@Valid @RequestBody GastoDTO gastoDTO) {
        Gasto nuevoGasto = gastoService.crearGasto(gastoDTO);
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

     // Endpoint para editar un gasto
     @PutMapping("/{id}")
     public ResponseEntity<Gasto> editarGasto(@PathVariable Long id, @RequestBody Gasto gastoActualizado) {
         Gasto gastoEditado = gastoService.editarGasto(id, gastoActualizado);
         return ResponseEntity.ok(gastoEditado);
     }
 }





