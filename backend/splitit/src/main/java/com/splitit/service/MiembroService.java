package com.splitit.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.MiembroRepository;

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
        if (miembro.getSaldo() == null) {
            miembro.setSaldo(BigDecimal.ZERO);
        }
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
        miembro.setSaldo(BigDecimal.ZERO);
        return miembroRepository.save(miembro);
    }

    // ✅ Método añadido para actualizar el saldo u otros datos de un miembro
    public Miembro actualizarMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    // Método para eliminar un miembro de un grupo
    // Este método no elimina al usuario, solo lo elimina del grupo
    public void eliminarMiembro(Long idMiembro) {
        Miembro miembro = miembroRepository.findById(idMiembro)
            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
    
        // Elimina todas las deudas asociadas
        if (miembro.getDeudasPendientes() != null) {
            miembro.getDeudasPendientes().clear();
        }
    
        // Elimina todos los gastos pagados
        if (miembro.getGastosPagados() != null) {
            miembro.getGastosPagados().clear();
        }
    
        // Muy importante: desvincula al miembro de su grupo y usuario
        miembro.setGrupo(null);
        miembro.setUsuario(null);
    
        miembroRepository.delete(miembro);
    }
    
    
    
}
