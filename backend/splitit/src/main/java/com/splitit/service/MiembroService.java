package com.splitit.service;

import java.util.List;
import com.splitit.model.Miembro;
import com.splitit.model.Grupo;
import com.splitit.model.Usuario;
import com.splitit.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    @Lazy
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    public Miembro crearMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    public Miembro buscarPorId(Long id) {
        return miembroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
    }

    public List<Miembro> obtenerTodos() {
        return miembroRepository.findAll();
    }

    // Método para invitar a un usuario a un grupo con rol "MEMBER"
    public Miembro invitarMiembro(Long idGrupo, Long idUsuario) {
        // Obtener el grupo
        Grupo grupo = grupoService.buscarPorId(idGrupo);
        // Obtener el usuario a invitar
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        // Crear el miembro con rol "MEMBER"
        Miembro miembro = new Miembro(usuario, grupo, "MEMBER");
        miembro.setSaldoActual(0);
        return miembroRepository.save(miembro);
    }
}
