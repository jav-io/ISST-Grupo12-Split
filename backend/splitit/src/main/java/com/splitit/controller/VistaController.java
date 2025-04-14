package com.splitit.controller;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.service.GrupoService;

import jakarta.servlet.http.HttpSession;

import com.splitit.service.GastoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;


@Controller
public class VistaController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GastoService gastoService;

    // Página de inicio
    @GetMapping("/")
    public String inicio(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/dashboard";
        }
        return "index";
    }
    

  @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(usuario.getIdUsuario());
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
    public String crearGrupo(@ModelAttribute GrupoDTO grupoDTO, HttpSession session) {
        Usuario creador = (Usuario) session.getAttribute("usuarioLogueado");
        if (creador == null) return "redirect:/login";

        grupoDTO.setIdCreador(creador.getIdUsuario());
        grupoService.crearGrupoDesdeDTO(grupoDTO); // solo pasamos grupoDTO

        return "redirect:/dashboard";
    }

    


    // Vista de detalle de grupo con gastos asociados
    @GetMapping("/detalle-grupo/{id}")
    public String mostrarDetalleGrupo(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Grupo grupo = grupoService.obtenerGrupoPorId(id);

        boolean esMiembro = grupo.getMiembros()
            .stream()
            .anyMatch(m -> m.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()));

        if (!esMiembro) {
            return "redirect:/dashboard"; // o mostrar error
        }

        // Obtenemos los gastos con sus participantes correctamente mapeados
        List<GastoConParticipantesDTO> gastos = gastoService.obtenerGastosConParticipantes()
            .stream()
            .filter(g -> g.getIdGrupo().equals(id)) // Solo los del grupo actual
            .toList();

        model.addAttribute("grupo", grupo);
        model.addAttribute("gastos", gastos);
        return "detalle-grupo";
    }
        


    // Vista para formulario de añadir gasto
    @GetMapping("/añadir-gasto/{idGrupo}")
    public String mostrarFormularioAñadirGasto(@PathVariable Long idGrupo, Model model) {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setIdGrupo(idGrupo);
        model.addAttribute("gasto", gastoDTO);

        Grupo grupo = grupoService.obtenerGrupoPorId(idGrupo);
        model.addAttribute("participantes", grupo.getMiembros());

        return "añadir-gasto";
    }

    
    
    

    // Procesamiento del formulario para añadir gasto
    @PostMapping("/añadir-gasto")
    public String añadirGasto(@ModelAttribute GastoDTO gastoDTO,
                              @RequestParam List<Long> idParticipantes,
                              HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
    
        Long idMiembro = grupoService
            .buscarPorId(gastoDTO.getIdGrupo())
            .getMiembros()
            .stream()
            .filter(m -> m.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No eres miembro de este grupo"))
            .getIdMiembro();
    
        gastoDTO.setIdPagador(idMiembro);
        gastoDTO.setIdParticipantes(idParticipantes); // importante
        gastoService.crearGasto(gastoDTO);
        return "redirect:/detalle-grupo/" + gastoDTO.getIdGrupo();
    }
    
    

    // Vista para formulario de editar gasto
    @GetMapping("/editar-gasto/{id}")
    public String mostrarFormularioEditarGasto(@PathVariable Long id, Model model) {
        Gasto gasto = gastoService.buscarPorId(id);
        Grupo grupo = gasto.getGrupo();
        List<Miembro> miembros = grupo.getMiembros();

        List<Long> gastoParticipantesIds = gasto.getDeudas().stream()
            .map(deuda -> deuda.getDeudor().getIdMiembro())
            .toList();

        model.addAttribute("gasto", gasto);
        model.addAttribute("participantes", miembros); // necesario para el formulario
        model.addAttribute("miembros", miembros); // opcional, si lo usas en otra parte
        model.addAttribute("gastoParticipantesIds", gastoParticipantesIds);

        return "editar-gasto";
    }


    // Procesamiento del formulario para editar gasto
    @PostMapping("/editar-gasto")
    public String actualizarGasto(
            @RequestParam Long id,
            @RequestParam String descripcion,
            @RequestParam Float monto,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam Long idGrupo,
            @RequestParam Long idPagador,
            @RequestParam List<Long> idParticipantes
    ) {
        gastoService.editarGastoConParticipantes(id, descripcion, monto, fecha, idGrupo, idPagador, idParticipantes);
        return "redirect:/detalle-grupo/" + idGrupo;
    }


    //redirige a saldos del grupo
    @GetMapping("/saldo-grupo/{id}")
    public String mostrarSaldoGrupo(@PathVariable Long id, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        List<SaldoGrupoDTO> saldos = grupoService.consultarSaldosGrupo(id);

        model.addAttribute("grupo", grupo);
        model.addAttribute("saldos", saldos);

        return "saldo-grupo";
    }


}
