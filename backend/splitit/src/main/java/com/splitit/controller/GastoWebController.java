
package com.splitit.controller;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.splitit.model.Gasto;
import com.splitit.service.GastoService;
import com.splitit.service.UsuarioService;



@Controller
public class GastoWebController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/eliminar-gasto/{id}")
    public String eliminarGasto(@PathVariable Long id, Principal principal, RedirectAttributes redirect) {
        Gasto gasto = gastoService.buscarPorId(id);
        Long idUsuario = usuarioService.buscarPorEmail(principal.getName()).get().getId();

        boolean esPagador = gasto.getPagador().getUsuario().getId().equals(idUsuario);
        boolean esAdmin = gasto.getGrupo().getMiembros().stream()
            .anyMatch(m -> m.getUsuario().getId().equals(idUsuario) && "ADMIN".equals(m.getRolEnGrupo()));

        if (!esPagador && !esAdmin) {
            redirect.addFlashAttribute("error", "No tienes permisos para eliminar este gasto.");
            return "redirect:/detalle-grupo/" + gasto.getGrupo().getId();
        }

        gastoService.eliminarGasto(id);
        redirect.addFlashAttribute("mensaje", "Gasto eliminado correctamente.");
        return "redirect:/detalle-grupo/" + gasto.getGrupo().getId();
    }
}
