package com.splitit.controller;

import com.splitit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/migrar-contrasenas")
    public String migrarContrasenas(RedirectAttributes redirectAttributes) {
        try {
            usuarioService.migrarContrasenas();
            redirectAttributes.addFlashAttribute("mensaje", "Contraseñas migradas exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al migrar contraseñas: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
} 