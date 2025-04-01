package com.splitit.service;

import com.splitit.model.Miembro;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.model.Usuario;
import com.splitit.dto.GrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Deuda;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MiembroService miembroService;

    @Autowired
<<<<<<< HEAD
    private UsuarioService usuarioService; // Para obtener el usuario creador

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

=======
    private UsuarioService usuarioService;
>>>>>>> a59245116f3c0fa0e44ed810655613636af44250

    public Grupo crearGrupo(GrupoDTO grupoDTO) {
        if (grupoDTO.getNombre() == null || grupoDTO.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del grupo es obligatorio.");
        }

        Grupo grupo = new Grupo();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setDescripcion(grupoDTO.getDescripcion());
        grupo.setFechaCreacion(new Date());
        Grupo grupoGuardado = grupoRepository.save(grupo);

        Usuario usuarioCreador = usuarioService.buscarPorId(grupoDTO.getIdCreador());
        Miembro miembroAdmin = new Miembro(usuarioCreador, grupoGuardado, "ADMIN");
        miembroAdmin.setSaldoActual(0);
        miembroService.crearMiembro(miembroAdmin);

        return grupoGuardado;
    }

    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado."));
    }

    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    // Método para consultar saldos en un grupo
    public List<SaldoGrupoDTO> consultarSaldosGrupo(Long idGrupo) {
    Grupo grupo = buscarPorId(idGrupo);
    List<Miembro> miembros = grupo.getMiembros();

    List<SaldoGrupoDTO> saldos = new ArrayList<>();

    for (Miembro miembro : miembros) {
        Usuario usuario = miembro.getUsuario();
        if (usuario == null) continue;

        saldos.add(new SaldoGrupoDTO(
            usuario.getIdUsuario(),
            usuario.getNombre(),
            miembro.getSaldoActual()
        ));
    }

<<<<<<< HEAD
    // Método 1: obtener grupos por usuario
public List<Grupo> obtenerGruposPorUsuario(Long idUsuario) {
    return grupoRepository.findByMiembros_Usuario_Id(idUsuario);
}

// Método 2: crear grupo a partir de un DTO (alias del ya existente)
public void crearGrupoDesdeDTO(GrupoDTO grupoDTO) {
    Grupo grupo = new Grupo();
    grupo.setNombre(grupoDTO.getNombre());
    grupo.setDescripcion(grupoDTO.getDescripcion());
    grupo.setFechaCreacion(new Date());

    Grupo grupoGuardado = grupoRepository.save(grupo);

    // Simular añadir al creador como miembro
    Miembro miembro = new Miembro();
    miembro.setGrupo(grupoGuardado);
    Usuario usuario = usuarioRepository.findById(grupoDTO.getIdCreador())
    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    miembro.setUsuario(usuario);
    miembroRepository.save(miembro);
}


// Método 3: obtener grupo por ID
public Grupo obtenerGrupoPorId(Long id) {
    return buscarPorId(id);
}

public GrupoDTO obtenerGrupoDTOporId(Long id) {
    Grupo grupo = grupoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    
    GrupoDTO dto = new GrupoDTO();
    dto.setNombre(grupo.getNombre());
    dto.setDescripcion(grupo.getDescripcion());
    dto.setIdCreador(grupo.getIdCreador()); // ✅
    dto.setFechaCreacion(grupo.getFechaCreacion());
    return dto;
=======
    return saldos;
>>>>>>> a59245116f3c0fa0e44ed810655613636af44250
}

}
