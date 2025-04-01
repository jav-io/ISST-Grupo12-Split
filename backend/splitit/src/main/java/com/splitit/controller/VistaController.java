package com.splitit.controller;

import com.splitit.dto.GrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.service.GrupoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VistaController {

    @Autowired
    private GrupoService grupoService;

    // Página de inicio
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    // Mostrar el dashboard con los grupos del usuario
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long idUsuarioSimulado = 2L; // Usar usuario simulado hasta que haya login
        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(idUsuarioSimulado);
        model.addAttribute("grupos", grupos);
        return "dashboard"; // Renderiza dashboard.html
    }

    // Formulario para crear grupo
    @GetMapping("/crear-grupo")
    public String mostrarFormularioCrearGrupo(Model model) {
        model.addAttribute("grupoDTO", new GrupoDTO());
        return "crear-grupo";
    }

    // Procesamiento del formulario para crear grupo
    @PostMapping("/crear-grupo")
    public String crearGrupo(@ModelAttribute GrupoDTO grupoDTO) {
        grupoDTO.setIdCreador(2L); // ID simulado
        grupoService.crearGrupoDesdeDTO(grupoDTO);
        return "redirect:/dashboard";
    }


    // Vista de detalle de grupo (pendiente implementar)
    @GetMapping("/detalle-grupo/{id}")
    public String detalleGrupo(@PathVariable Long id, Model model) {
        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        model.addAttribute("grupo", grupo);
        return "detalle-grupo";
    }
}

