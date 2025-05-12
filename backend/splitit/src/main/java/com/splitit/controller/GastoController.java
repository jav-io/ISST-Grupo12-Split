package com.splitit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.dto.GastoResponseDTO;
import com.splitit.model.Gasto;
import com.splitit.service.GastoService;
import com.splitit.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private UsuarioService usuarioService;

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
