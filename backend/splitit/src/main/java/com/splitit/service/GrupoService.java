package com.splitit.service;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.UsuarioRepository;
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

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public List<Grupo> obtenerGruposPorUsuario(Long idUsuario) {
        return grupoRepository.findByMiembros_Usuario_Id(idUsuario);
    }

    public Grupo crearGrupoDesdeDTO(GrupoDTO grupoDTO) {
        Grupo grupo = new Grupo();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setDescripcion(grupoDTO.getDescripcion());
        grupo.setFechaCreacion(new Date());

        Grupo grupoGuardado = grupoRepository.save(grupo);

        Miembro miembro = new Miembro();
        miembro.setGrupo(grupoGuardado);
        Usuario usuario = usuarioRepository.findById(grupoDTO.getIdCreador())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        miembro.setUsuario(usuario);
        miembroRepository.save(miembro);

        return grupoGuardado;
    }

    public Grupo obtenerGrupoPorId(Long id) {
        return buscarPorId(id);
    }

    public GrupoDTO obtenerGrupoDTOporId(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        GrupoDTO dto = new GrupoDTO();
        dto.setNombre(grupo.getNombre());
        dto.setDescripcion(grupo.getDescripcion());
        dto.setIdCreador(grupo.getIdCreador());
        dto.setFechaCreacion(grupo.getFechaCreacion());
        return dto;
    }
}
