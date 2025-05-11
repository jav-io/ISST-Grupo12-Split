package com.splitit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.service.GrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;


@PostMapping("/grupos/crear")
public String crearGrupo(@ModelAttribute("grupo") GrupoDTO grupoDTO, Model model) {
    try {
        grupoService.crearGrupoDesdeDTO(grupoDTO);
        return "redirect:/grupos";
    } catch (RuntimeException ex) {
        model.addAttribute("grupo", grupoDTO); // para mantener los datos
        model.addAttribute("error", ex.getMessage()); // aquí va el mensaje del error
        return "grupo_formulario"; // o como se llame tu plantilla del formulario
    }
}


    @GetMapping
    public ResponseEntity<List<Grupo>> obtenerTodos() {
        return ResponseEntity.ok(grupoService.obtenerTodos());
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Long idGrupo) {
        return ResponseEntity.ok(grupoService.buscarPorId(idGrupo));
    }

    @GetMapping("/{idGrupo}/saldos")
    public ResponseEntity<List<SaldoGrupoDTO>> consultarSaldos(@PathVariable Long idGrupo) {
        List<SaldoGrupoDTO> saldos = grupoService.consultarSaldosGrupo(idGrupo);
        return ResponseEntity.ok(saldos);
    }
}
