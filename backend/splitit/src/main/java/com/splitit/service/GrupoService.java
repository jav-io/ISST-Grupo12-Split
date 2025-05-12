package com.splitit.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitit.dto.GrupoDTO;
import com.splitit.dto.ParticipanteDTO;
import com.splitit.dto.SaldoGrupoDTO;
import com.splitit.dto.TransferenciaDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.UsuarioRepository;

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

                if (optionalUsuario.isEmpty()) {
                    throw new RuntimeException("El usuario con email " + participante.getEmail() + " no está registrado.");
                }

                Usuario usuario = optionalUsuario.get();
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

    public void cerrarGrupo(Long grupoId, Long usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    
        boolean esAdmin = grupo.getMiembros().stream()
            .anyMatch(m -> m.getUsuario().getId().equals(usuarioId) && "ADMIN".equals(m.getRolEnGrupo()));
    
        if (!esAdmin) {
            throw new RuntimeException("Solo el administrador puede cerrar el grupo");
        }
    
        if (grupo.isCerrado()) {
            throw new RuntimeException("El grupo ya está cerrado");
        }
    
        grupo.setCerrado(true);
        grupoRepository.save(grupo);
    }

    public List<TransferenciaDTO> calcularTransferenciasSugeridas(Long grupoId) {
        Grupo grupo = buscarPorId(grupoId);
        List<Miembro> miembros = grupo.getMiembros();

        List<TransferenciaDTO> transferencias = new ArrayList<>();

        List<Miembro> deudores = new ArrayList<>();
        List<Miembro> acreedores = new ArrayList<>();

        for (Miembro m : miembros) {
            if (m.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
                deudores.add(m);
            } else if (m.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
                acreedores.add(m);
            }
        }

        deudores.sort(Comparator.comparing(m -> m.getSaldo()));
        acreedores.sort((m1, m2) -> m2.getSaldo().compareTo(m1.getSaldo()));

        for (Miembro deudor : deudores) {
            BigDecimal deuda = deudor.getSaldo().abs();
            for (Iterator<Miembro> it = acreedores.iterator(); it.hasNext() && deuda.compareTo(BigDecimal.ZERO) > 0;) {
                Miembro acreedor = it.next();
                BigDecimal disponible = acreedor.getSaldo();

                BigDecimal monto = deuda.min(disponible);
                if (monto.compareTo(BigDecimal.ZERO) > 0) {
                    transferencias.add(new TransferenciaDTO(
    deudor.getUsuario().getNombre(),
    acreedor.getUsuario().getNombre(),
    monto,
    grupo.getNombre()
));

                    deuda = deuda.subtract(monto);
                    acreedor.setSaldo(disponible.subtract(monto));
                }

                if (acreedor.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
                    it.remove();
                }
            }
        }

        return transferencias;
    }
}
