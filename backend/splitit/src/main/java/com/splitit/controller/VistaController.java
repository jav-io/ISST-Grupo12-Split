package com.splitit.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.dto.GrupoDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.service.GastoService;
import com.splitit.service.GrupoService;
import com.splitit.service.MiembroService;
import com.splitit.service.UsuarioService;

@Controller
public class VistaController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MiembroService miembroService;

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
public String crearGrupo(@ModelAttribute GrupoDTO grupoDTO, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        return "redirect:/login";
    }

    try {
        Usuario creador = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        grupoDTO.setIdCreador(creador.getId());
        grupoService.crearGrupoDesdeDTO(grupoDTO);
        return "redirect:/dashboard";
    } catch (RuntimeException ex) {
        model.addAttribute("grupo", grupoDTO);
        model.addAttribute("error", ex.getMessage());
        return "crear-grupo"; // Asegúrate de que esta plantilla exista
    }
}


    @GetMapping("/detalle-grupo/{id}")
    public String mostrarDetalleGrupo(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
    
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuarioId", usuario.getId());
    
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
    
        // Verifica si el usuario actual es el único admin
        long totalAdmins = grupo.getMiembros().stream()
                .filter(m -> "ADMIN".equals(m.getRolEnGrupo()))
                .count();
    
        boolean esUnicoAdmin = esAdmin && totalAdmins == 1;
        model.addAttribute("esUnicoAdmin", esUnicoAdmin);
    
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
        model.addAttribute("idGrupo", idGrupo);
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
    
        String emailActual = auth.getName();
        Gasto gasto = gastoService.buscarPorId(id);
        Grupo grupo = gasto.getGrupo();
    
        // Redirigir si el grupo está cerrado
        if (grupo.isCerrado()) {
            return "redirect:/detalle-grupo/" + grupo.getId();
        }
    
        // Verificar permisos: solo admin o pagador puede editar
        boolean esAdmin = grupo.getMiembros().stream()
            .anyMatch(m -> m.getUsuario().getEmail().equals(emailActual) && "ADMIN".equals(m.getRolEnGrupo()));
    
        boolean esPagador = gasto.getPagador().getUsuario().getEmail().equals(emailActual);
    
        if (!esAdmin && !esPagador) {
            return "redirect:/detalle-grupo/" + grupo.getId();
        }
    
        // Preparar el DTO
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setId(gasto.getId());
        gastoDTO.setDescripcion(gasto.getDescripcion());
        gastoDTO.setMonto(gasto.getMonto());
        gastoDTO.setFecha(gasto.getFecha());
        gastoDTO.setPagadorId(gasto.getPagador().getId());
        gastoDTO.setGrupoId(grupo.getId());
        gastoDTO.setCategoria(gasto.getCategoria());
        gastoDTO.setParticipantesIds(
            gasto.getDeudas().stream()
                .map(deuda -> deuda.getDeudor().getId())
                .collect(Collectors.toList())
        );
    
        List<String> categorias = Arrays.asList("COMIDA", "TRANSPORTE", "ALOJAMIENTO", "ENTRETENIMIENTO", "COMPRAS", "OTROS");
    
        model.addAttribute("gasto", gastoDTO);
        model.addAttribute("participantes", grupo.getMiembros());
        model.addAttribute("grupoId", grupo.getId());
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

    @PostMapping("/grupo/{id}/añadir-miembro")
public String añadirMiembroAlGrupo(@PathVariable Long id,
                                   @RequestParam String nombre,
                                   @RequestParam String email,
                                   RedirectAttributes redirect) {
    // Buscar o crear usuario
    Usuario usuario = usuarioService.buscarOCrearPorEmail(email, nombre);

    // Buscar grupo
    Grupo grupo = grupoService.buscarPorId(id);

    // Verificar si ya es miembro
    boolean yaEsMiembro = grupo.getMiembros().stream()
        .anyMatch(m -> m.getUsuario().getEmail().equals(email));
    if (yaEsMiembro) {
        redirect.addFlashAttribute("error", "Este usuario ya es miembro del grupo.");
        return "redirect:/detalle-grupo/" + id;
    }

    // Crear miembro con rol "MIEMBRO" y saldo 0
    Miembro nuevoMiembro = new Miembro();
    nuevoMiembro.setGrupo(grupo);
    nuevoMiembro.setUsuario(usuario);
    nuevoMiembro.setRolEnGrupo("MIEMBRO");
    nuevoMiembro.setSaldo(BigDecimal.ZERO);

    miembroService.crearMiembro(nuevoMiembro);

    redirect.addFlashAttribute("mensaje", "Miembro añadido correctamente.");
    return "redirect:/detalle-grupo/" + id;
}

// Método para eliminar un miembro del grupo
@PostMapping("/grupo/{idGrupo}/eliminar-miembro/{idMiembro}")
public String eliminarMiembro(@PathVariable Long idGrupo,
                              @PathVariable Long idMiembro,
                              RedirectAttributes redirect) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        return "redirect:/login";
    }

    Usuario usuarioActual = usuarioService.buscarPorEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Grupo grupo = grupoService.obtenerGrupoPorId(idGrupo);
    Miembro miembroAEliminar = miembroService.buscarPorId(idMiembro);

    if (miembroAEliminar == null) {
        redirect.addFlashAttribute("error", "El miembro no existe.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }

    boolean esAdmin = grupo.getMiembros().stream()
        .anyMatch(m -> m.getUsuario().getId().equals(usuarioActual.getId()) && "ADMIN".equals(m.getRolEnGrupo()));

    if (!esAdmin) {
        redirect.addFlashAttribute("error", "No tienes permiso para eliminar miembros.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }

    if ("ADMIN".equals(miembroAEliminar.getRolEnGrupo())) {
        long cantidadAdmins = grupo.getMiembros().stream()
            .filter(m -> "ADMIN".equals(m.getRolEnGrupo()))
            .count();

        if (cantidadAdmins <= 1) {
            redirect.addFlashAttribute("error", "No puedes eliminar al único administrador del grupo.");
            return "redirect:/detalle-grupo/" + idGrupo;
        }
    }

    // Verifica que el saldo sea 0
    if (miembroAEliminar.getSaldoActual().compareTo(BigDecimal.ZERO) != 0) {
        redirect.addFlashAttribute("error", "No se puede eliminar al miembro: debe saldar sus deudas antes de realizar esta operación.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }


    // Llamada al método actualizado
    miembroService.eliminarMiembro(idMiembro);

    redirect.addFlashAttribute("mensaje", "Miembro eliminado correctamente.");
    return "redirect:/detalle-grupo/" + idGrupo;
}


// Método para abandonar un grupo
@PostMapping("/grupo/{idGrupo}/abandonar")
public String abandonarGrupo(@PathVariable Long idGrupo, RedirectAttributes redirect) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        return "redirect:/login";
    }

    Usuario usuarioActual = usuarioService.buscarPorEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Grupo grupo = grupoService.obtenerGrupoPorId(idGrupo);

    // Buscar al miembro que representa al usuario
    Miembro miembro = grupo.getMiembros().stream()
            .filter(m -> m.getUsuario().getId().equals(usuarioActual.getId()))
            .findFirst()
            .orElse(null);

    if (miembro == null) {
        redirect.addFlashAttribute("error", "No eres miembro de este grupo.");
        return "redirect:/dashboard";
    }

    // No permitir que el único admin se vaya
    if ("ADMIN".equals(miembro.getRolEnGrupo())) {
        long cantidadAdmins = grupo.getMiembros().stream()
                .filter(m -> "ADMIN".equals(m.getRolEnGrupo()))
                .count();

        if (cantidadAdmins <= 1) {
            redirect.addFlashAttribute("error", "Eres el único administrador. Asigna otro antes de abandonar.");
            return "redirect:/detalle-grupo/" + idGrupo;
        }
    }

    // Solo puede abandonar si no tiene saldo pendiente
    if (miembro.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
        redirect.addFlashAttribute("error", "No puedes abandonar el grupo: debes saldar tus deudas antes de realizar esta operación.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }
    

    miembroService.eliminarMiembro(miembro.getId());
    redirect.addFlashAttribute("mensaje", "Has abandonado el grupo correctamente.");
    return "redirect:/dashboard";
}

// Método para cambiar el rol de un miembro
@PostMapping("/grupo/{idGrupo}/cambiar-rol/{idMiembro}")
public String cambiarRolMiembro(@PathVariable Long idGrupo,
                                @PathVariable Long idMiembro,
                                RedirectAttributes redirect) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        return "redirect:/login";
    }

    Usuario usuarioActual = usuarioService.buscarPorEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Grupo grupo = grupoService.obtenerGrupoPorId(idGrupo);
    Miembro miembro = miembroService.buscarPorId(idMiembro);

    boolean esAdminActual = grupo.getMiembros().stream()
            .anyMatch(m -> m.getUsuario().getId().equals(usuarioActual.getId()) && "ADMIN".equals(m.getRolEnGrupo()));

    if (!esAdminActual) {
        redirect.addFlashAttribute("error", "No tienes permiso para cambiar roles.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }

    if (!miembro.getGrupo().getId().equals(idGrupo)) {
        redirect.addFlashAttribute("error", "El miembro no pertenece a este grupo.");
        return "redirect:/detalle-grupo/" + idGrupo;
    }

    // Si es admin, lo pasamos a miembro. Si es miembro, lo pasamos a admin.
    if ("ADMIN".equals(miembro.getRolEnGrupo())) {
        long admins = grupo.getMiembros().stream().filter(m -> "ADMIN".equals(m.getRolEnGrupo())).count();
        if (admins <= 1) {
            redirect.addFlashAttribute("error", "No puedes quitar el rol de admin al único administrador.");
            return "redirect:/detalle-grupo/" + idGrupo;
        }
        miembro.setRolEnGrupo("MIEMBRO");
    } else {
        miembro.setRolEnGrupo("ADMIN");
    }

    miembroService.actualizarMiembro(miembro);
    redirect.addFlashAttribute("mensaje", "Rol actualizado correctamente.");
    return "redirect:/detalle-grupo/" + idGrupo;
}




}