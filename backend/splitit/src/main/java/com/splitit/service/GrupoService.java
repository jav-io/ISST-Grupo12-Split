package com.splitit.service;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private MiembroService miembroService;

    @Autowired
    private UsuarioService usuarioService;

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
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    }

    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

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

        return saldos;
    }

    // 🔁 Métodos añadidos para compatibilidad con VistaController

    public List<Grupo> obtenerGruposPorUsuario(Long idUsuario) {
        List<Grupo> grupos = new ArrayList<>();
        for (Grupo grupo : grupoRepository.findAll()) {
            for (Miembro miembro : grupo.getMiembros()) {
                if (miembro.getUsuario() != null && miembro.getUsuario().getIdUsuario().equals(idUsuario)) {
                    grupos.add(grupo);
                    break;
                }
            }
        }
        return grupos;
    }

    public Grupo crearGrupoDesdeDTO(GrupoDTO grupoDTO) {
        return crearGrupo(grupoDTO); // Alias
    }

    public Grupo obtenerGrupoPorId(Long id) {
        return buscarPorId(id); // Alias
    }
}
