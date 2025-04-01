package com.splitit.controller;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.service.GrupoService;
import com.splitit.service.GastoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VistaController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GastoService gastoService;

    // Página de inicio
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    // Mostrar el dashboard con los grupos del usuario
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long idUsuarioSimulado = 2L;
        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(idUsuarioSimulado);
        model.addAttribute("grupos", grupos);
        return "dashboard";
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

    // Vista de detalle de grupo con gastos asociados
    @GetMapping("/detalle-grupo/{id}")
    public String mostrarDetalleGrupo(@PathVariable Long id, Model model) {
        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        List<Gasto> gastos = gastoService.obtenerGastosPorGrupo(id);
        model.addAttribute("grupo", grupo);
        model.addAttribute("gastos", gastos);
        return "detalle-grupo";
    }


    // Vista para formulario de añadir gasto
    @GetMapping("/añadir-gasto/{idGrupo}")
    public String mostrarFormularioAñadirGasto(@PathVariable Long idGrupo, Model model) {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setIdGrupo(idGrupo); // ESTO es esencial
        model.addAttribute("gasto", gastoDTO);
        return "añadir-gasto";
    }
    

    // Procesamiento del formulario para añadir gasto
    @PostMapping("/añadir-gasto")
    public String añadirGasto(@ModelAttribute GastoDTO gastoDTO) {
        gastoDTO.setIdUsuarioPagador(2L); // Simulado
        gastoService.crearGasto(gastoDTO);
        return "redirect:/detalle-grupo/" + gastoDTO.getIdGrupo();
    }
    

    // Vista para formulario de editar gasto
    @GetMapping("/editar-gasto/{id}")
    public String mostrarFormularioEditarGasto(@PathVariable Long id, Model model) {
        Gasto gasto = gastoService.obtenerGastoPorId(id);
        model.addAttribute("gasto", gasto);
        return "editar-gasto";
    }

    // Procesamiento del formulario para editar gasto
    @PostMapping("/editar-gasto")
    public String editarGasto(@ModelAttribute Gasto gasto) {
        gastoService.actualizarGasto(gasto);
        return "redirect:/detalle-grupo/" + gasto.getGrupo().getIdGrupo();
    }
}
