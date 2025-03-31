package com.splitit.service;

import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.dto.GrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MiembroService miembroService;

    @Autowired
    private UsuarioService usuarioService; // Para obtener el usuario creador


    public Grupo crearGrupo(GrupoDTO grupoDTO) {
        // Validación adicional (aunque el DTO ya tiene validaciones)
        if (grupoDTO.getNombre() == null || grupoDTO.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del grupo es obligatorio.");
        }
        
        // Crear la entidad Grupo
        Grupo grupo = new Grupo();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setDescripcion(grupoDTO.getDescripcion());
        grupo.setFechaCreacion(new Date());
        Grupo grupoGuardado = grupoRepository.save(grupo);

        // Obtener el usuario creador
        Usuario usuarioCreador = usuarioService.buscarPorId(grupoDTO.getIdCreador());
        
        // Crear el miembro que asocia al usuario con el grupo, asignándole el rol "ADMIN"
        Miembro miembroAdmin = new Miembro(usuarioCreador, grupoGuardado, "ADMIN");
        miembroAdmin.setSaldoActual(0);
        miembroService.crearMiembro(miembroAdmin);
        
        return grupoGuardado;
    }

    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    }
}
