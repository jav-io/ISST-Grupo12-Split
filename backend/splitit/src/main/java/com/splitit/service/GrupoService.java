package com.splitit.service;

import com.splitit.DTO.GrupoDTO;
import com.splitit.DTO.ParticipanteDTO;
import com.splitit.DTO.SaldoGrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

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
        grupo.setIdCreador(grupoDTO.getIdCreador());
        Grupo grupoGuardado = grupoRepository.save(grupo);

        Usuario usuarioCreador = usuarioRepository.findById(grupoDTO.getIdCreador())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Miembro miembroAdmin = new Miembro(usuarioCreador, grupoGuardado, "ADMIN");
        miembroAdmin.setSaldo(BigDecimal.ZERO);
        miembroRepository.save(miembroAdmin);

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
                    usuario.getId(),
                    usuario.getNombre(),
                    miembro.getSaldo()
            ));
        }
        return saldos;
    }

    public List<Grupo> obtenerGruposPorUsuario(Long idUsuario) {
        return grupoRepository.findByMiembros_Usuario_Id(idUsuario);
    }

    public Grupo crearGrupoDesdeDTO(GrupoDTO grupoDTO) {
        if (grupoDTO.getNombre() == null || grupoDTO.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del grupo es obligatorio.");
        }

        Grupo grupo = new Grupo();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setDescripcion(grupoDTO.getDescripcion());
        grupo.setFechaCreacion(new Date());
        grupo.setIdCreador(grupoDTO.getIdCreador());

        Grupo grupoGuardado = grupoRepository.save(grupo);

        Usuario usuarioCreador = usuarioRepository.findById(grupoDTO.getIdCreador())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Miembro miembroAdmin = new Miembro(usuarioCreador, grupoGuardado, "ADMIN");
        miembroAdmin.setSaldo(BigDecimal.ZERO);
        miembroRepository.save(miembroAdmin);

        if (grupoDTO.getMiembros() != null) {
            for (ParticipanteDTO participante : grupoDTO.getMiembros()) {
                if (participante.getEmail() == null || participante.getEmail().trim().isEmpty() || 
                    participante.getEmail().equals(usuarioCreador.getEmail())) {
                    continue;
                }

                Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(participante.getEmail());
                Usuario usuario;

                if (optionalUsuario.isPresent()) {
                    usuario = optionalUsuario.get();
                } else {
                    usuario = new Usuario();
                    usuario.setNombre(participante.getNombre());
                    usuario.setEmail(participante.getEmail());
                    usuario = usuarioRepository.save(usuario);
                }

                Miembro nuevoMiembro = new Miembro(usuario, grupoGuardado, "MIEMBRO");
                nuevoMiembro.setSaldo(BigDecimal.ZERO);
                miembroRepository.save(nuevoMiembro);
            }
        }

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
