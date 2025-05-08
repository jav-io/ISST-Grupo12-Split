package com.splitit.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.splitit.DTO.GastoConParticipantesDTO;
import com.splitit.DTO.GastoDTO;
import com.splitit.DTO.GrupoDTO;
import com.splitit.DTO.SaldoGrupoDTO;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Usuario;
import com.splitit.service.GastoService;
import com.splitit.service.GrupoService;
import com.splitit.service.UsuarioService;

@Controller
public class VistaController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(usuario.getId());
        model.addAttribute("grupos", grupos);
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    @GetMapping("/crear-grupo")
    public String mostrarFormularioCrearGrupo(Model model) {
        model.addAttribute("grupoDTO", new GrupoDTO());
        return "crear-grupo";
    }

    @PostMapping("/crear-grupo")
    public String crearGrupo(@ModelAttribute GrupoDTO grupoDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Usuario creador = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        grupoDTO.setIdCreador(creador.getId());
        grupoService.crearGrupoDesdeDTO(grupoDTO);

        return "redirect:/dashboard";
    }

    @GetMapping("/detalle-grupo/{id}")
    public String mostrarDetalleGrupo(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = grupoService.obtenerGrupoPorId(id);

        boolean esMiembro = grupo.getMiembros()
                .stream()
                .anyMatch(m -> m.getUsuario().getId().equals(usuario.getId()));

        if (!esMiembro) {
            return "redirect:/dashboard";
        }

        boolean esAdmin = grupo.getMiembros().stream()
                .anyMatch(m -> m.getUsuario().getId().equals(usuario.getId()) && "ADMIN".equals(m.getRolEnGrupo()));
        model.addAttribute("esAdmin", esAdmin);

        List<GastoConParticipantesDTO> gastos = gastoService.obtenerGastosConParticipantes()
                .stream()
                .filter(g -> g.getGrupoId().equals(id))
                .toList();

        model.addAttribute("grupo", grupo);
        model.addAttribute("gastos", gastos);
        return "detalle-grupo";
    }

    @PostMapping("/grupo/{id}/cerrar")
    public String cerrarGrupoDesdeVista(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupoService.cerrarGrupo(id, usuario.getId());
        return "redirect:/detalle-grupo/" + id;
    }

    @GetMapping("/añadir-gasto/{idGrupo}")
    public String mostrarFormularioAñadirGasto(@PathVariable Long idGrupo, Model model) {
        Grupo grupo = grupoService.obtenerGrupoPorId(idGrupo);
        if (grupo.isCerrado()) {
            return "redirect:/detalle-grupo/" + idGrupo;
        }

        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setGrupoId(idGrupo);
        model.addAttribute("gasto", gastoDTO);
        model.addAttribute("participantes", grupo.getMiembros());
        return "añadir-gasto";
    }

    @PostMapping("/añadir-gasto")
    public String procesarAñadirGasto(@ModelAttribute GastoDTO gastoDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        try {
            Grupo grupo = grupoService.obtenerGrupoPorId(gastoDTO.getGrupoId());
            if (grupo.isCerrado()) {
                return "redirect:/detalle-grupo/" + gastoDTO.getGrupoId();
            }

            if (gastoDTO.getFecha() == null) {
                gastoDTO.setFecha(LocalDateTime.now());
            }
            gastoService.crearGasto(gastoDTO);
            return "redirect:/detalle-grupo/" + gastoDTO.getGrupoId();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/saldo-grupo/{id}")
    public String mostrarSaldoGrupo(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        List<SaldoGrupoDTO> saldos = grupoService.consultarSaldosGrupo(id);

        model.addAttribute("grupo", grupo);
        model.addAttribute("saldos", saldos);

        return "saldo-grupo";
    }

    @GetMapping("/recuperar-password")
    public String mostrarFormularioRecuperarPassword() {
        return "recuperar-password";
    }

    @GetMapping("/editar-gasto/{id}")
    public String mostrarFormularioEditarGasto(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Gasto gasto = gastoService.buscarPorId(id);
        if (gasto.getGrupo().isCerrado()) {
            return "redirect:/detalle-grupo/" + gasto.getGrupo().getId();
        }

        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setId(gasto.getId());
        gastoDTO.setDescripcion(gasto.getDescripcion());
        gastoDTO.setMonto(gasto.getMonto());
        gastoDTO.setFecha(gasto.getFecha());
        gastoDTO.setPagadorId(gasto.getPagador().getId());
        gastoDTO.setGrupoId(gasto.getGrupo().getId());
        gastoDTO.setCategoria(gasto.getCategoria());
        gastoDTO.setParticipantesIds(gasto.getDeudas().stream()
                .map(deuda -> deuda.getDeudor().getId())
                .collect(Collectors.toList()));

        List<String> categorias = Arrays.asList("COMIDA", "TRANSPORTE", "ALOJAMIENTO", "ENTRETENIMIENTO", "COMPRAS", "OTROS");

        model.addAttribute("gasto", gastoDTO);
        model.addAttribute("participantes", gasto.getGrupo().getMiembros());
        model.addAttribute("grupoId", gasto.getGrupo().getId());
        model.addAttribute("categorias", categorias);

        return "editar-gasto";
    }

    @PostMapping("/editar-gasto/{id}")
    public String procesarEditarGasto(@PathVariable Long id, @ModelAttribute GastoDTO gastoDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        try {
            Grupo grupo = grupoService.obtenerGrupoPorId(gastoDTO.getGrupoId());
            if (grupo.isCerrado()) {
                return "redirect:/detalle-grupo/" + gastoDTO.getGrupoId();
            }

            if (gastoDTO.getFecha() == null) {
                gastoDTO.setFecha(LocalDateTime.now());
            }

            gastoService.editarGastoConParticipantes(
                    id,
                    gastoDTO.getDescripcion(),
                    gastoDTO.getMonto(),
                    gastoDTO.getFecha(),
                    gastoDTO.getGrupoId(),
                    gastoDTO.getPagadorId(),
                    gastoDTO.getParticipantesIds()
            );

            return "redirect:/detalle-grupo/" + gastoDTO.getGrupoId();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
