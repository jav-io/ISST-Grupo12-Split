package com.splitit.controller;

import com.splitit.DTO.GastoDTO;
import com.splitit.DTO.GastoResponseDTO;
import com.splitit.DTO.GastoConParticipantesDTO;
import com.splitit.model.Gasto;
import com.splitit.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    // Endpoint para crear un gasto usando GastoDTO
    @PostMapping
    public ResponseEntity<Gasto> crearGasto(@Valid @RequestBody GastoDTO gastoDTO) {
        Gasto nuevoGasto = gastoService.crearGasto(gastoDTO);
        return ResponseEntity.ok(nuevoGasto);
    }

    @GetMapping("/response")
    public ResponseEntity<List<GastoResponseDTO>> obtenerGastosResponse() {
        return ResponseEntity.ok(gastoService.obtenerGastosResponse());
    }
    
    // Endpoint para obtener todos los gastos con participantes (incluye pagador con nombre)
    @GetMapping
    public ResponseEntity<List<GastoConParticipantesDTO>> obtenerTodosConParticipantes() {
        return ResponseEntity.ok(gastoService.obtenerGastosConParticipantes());
    }

    // Endpoint para buscar un gasto por ID
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
