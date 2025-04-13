package com.splitit.controller;

import com.splitit.model.Usuario;
import com.splitit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                 @RequestParam String password,
                                 Model model,
                                 HttpSession session) {
        try {
            Usuario usuario = usuarioService.autenticarUsuario(email, password);
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
    

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.crearUsuario(usuario);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}

