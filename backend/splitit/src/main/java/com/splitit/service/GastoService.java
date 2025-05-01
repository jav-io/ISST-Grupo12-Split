package com.splitit.service;

import com.splitit.DTO.GastoDTO;
import com.splitit.DTO.GastoResponseDTO;
import com.splitit.DTO.GastoConParticipantesDTO;
import com.splitit.DTO.ParticipanteDTO;
import com.splitit.model.Deuda;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GastoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.GrupoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private GrupoRepository grupoRepository;


    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Transactional
    public Gasto crearGasto(GastoDTO gastoDTO) {
        if (gastoDTO.getGrupoId() == null) {
            throw new RuntimeException("El ID del grupo es obligatorio");
        }

        Grupo grupo = grupoRepository.findById(gastoDTO.getGrupoId())
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        if (gastoDTO.getPagadorId() == null) {
            throw new RuntimeException("El ID del pagador es obligatorio");
        }

        Miembro pagador = miembroRepository.findById(gastoDTO.getPagadorId())
                .orElseThrow(() -> new RuntimeException("Pagador no encontrado"));

        if (!pagador.getGrupo().getId().equals(grupo.getId())) {
            throw new RuntimeException("El pagador no pertenece al grupo especificado");
        }

        Gasto gasto = new Gasto();
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setFecha(gastoDTO.getFecha() != null ? gastoDTO.getFecha() : LocalDateTime.now());
        gasto.setCategoria(gastoDTO.getCategoria());
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);

        // Guardar el gasto primero para tener un ID
        gasto = gastoRepository.save(gasto);

        // Si hay participantes específicos, usar esos, si no, usar todos los miembros del grupo
        List<Long> participantes = gastoDTO.getParticipantesIds() != null && !gastoDTO.getParticipantesIds().isEmpty() 
            ? gastoDTO.getParticipantesIds() 
            : grupo.getMiembros().stream().map(Miembro::getId).collect(Collectors.toList());

        // Calcular monto por persona
        BigDecimal montoPorPersona = gasto.getMonto().divide(
            BigDecimal.valueOf(participantes.size()),
            2,
            BigDecimal.ROUND_HALF_UP
        );

        // Crear deudas para cada participante excepto el pagador
        for (Long idMiembro : participantes) {
            if (!idMiembro.equals(pagador.getId())) {
                Miembro participante = miembroRepository.findById(idMiembro)
                        .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

                if (!participante.getGrupo().getId().equals(grupo.getId())) {
                    throw new RuntimeException("El participante no pertenece al grupo especificado");
                }

                Deuda deuda = new Deuda();
                deuda.setGasto(gasto);
                deuda.setDeudor(participante);
                deuda.setMonto(montoPorPersona);
                deuda.setFecha(gasto.getFecha());
                deudaRepository.save(deuda);

                // Actualizar saldo del deudor
                participante.setSaldo(participante.getSaldo().subtract(montoPorPersona));
                miembroRepository.save(participante);
            }
        }

        // Actualizar saldo del pagador
        BigDecimal totalRecuperado = montoPorPersona.multiply(BigDecimal.valueOf(participantes.size() - 1));
        pagador.setSaldo(pagador.getSaldo().add(totalRecuperado));
        miembroRepository.save(pagador);

        return gasto;
    }

    public List<GastoResponseDTO> obtenerGastosResponse() {
        List<Gasto> gastos = gastoRepository.findAll();
        return gastos.stream().map(gasto -> new GastoResponseDTO(
                gasto.getId(),
                gasto.getDescripcion(),
                gasto.getMonto(),
                gasto.getFecha(),
                gasto.getPagador().getUsuario().getNombre(),
                gasto.getGrupo().getNombre(),
                gasto.getCategoria()
        )).collect(Collectors.toList());
    }

    public List<GastoConParticipantesDTO> obtenerGastosConParticipantes() {
        List<Gasto> gastos = gastoRepository.findAll();
        return gastos.stream()
            .filter(gasto -> gasto.getGrupo() != null) // Filtrar gastos sin grupo
            .map(gasto -> {
                List<ParticipanteDTO> participantes = gasto.getGrupo().getMiembros().stream()
                        .map(miembro -> new ParticipanteDTO(
                                miembro.getId(),
                                miembro.getUsuario().getNombre(),
                                miembro.getUsuario().getEmail(),
                                miembro.getSaldo()
                        )).collect(Collectors.toList());

                return new GastoConParticipantesDTO(
                        gasto.getId(),
                        gasto.getDescripcion(),
                        gasto.getMonto(),
                        gasto.getFecha(),
                        gasto.getPagador().getUsuario().getNombre(),
                        gasto.getPagador().getId(),
                        gasto.getGrupo().getNombre(),
                        gasto.getGrupo().getId(),
                        gasto.getCategoria(),
                        participantes
                );
            }).collect(Collectors.toList());
    }

    public Gasto buscarPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
    }

    @Transactional
    public Gasto editarGasto(Long id, Gasto gastoActualizado) {
        Gasto gastoExistente = buscarPorId(id);
        gastoExistente.setDescripcion(gastoActualizado.getDescripcion());
        gastoExistente.setMonto(gastoActualizado.getMonto());
        gastoExistente.setFecha(gastoActualizado.getFecha());
        gastoExistente.setCategoria(gastoActualizado.getCategoria());
        return gastoRepository.save(gastoExistente);
    }

    public List<Gasto> obtenerTodos() {
        return gastoRepository.findAll();
    }

    public List<Gasto> obtenerGastosPorGrupo(Long grupoId) {
        return gastoRepository.findByGrupoId(grupoId);
    }

    @Transactional
    public void editarGastoConParticipantes(Long id, String descripcion, BigDecimal monto, LocalDateTime fecha,
                                        Long idGrupo, Long idPagador, List<Long> nuevosParticipantes) {
        Gasto gasto = buscarPorId(id);
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        Miembro pagador = miembroRepository.findById(idPagador)
                .orElseThrow(() -> new RuntimeException("Pagador no encontrado"));

        // Revertir saldos actuales antes de editar
        for (Deuda deuda : gasto.getDeudas()) {
            Miembro deudor = deuda.getDeudor();
            deudor.setSaldo(deudor.getSaldo().add(deuda.getMonto()));
            miembroRepository.save(deudor);
        }

        // Eliminar deudas previas
        deudaRepository.deleteAll(gasto.getDeudas());
        gasto.getDeudas().clear();

        // Actualizar info
        gasto.setDescripcion(descripcion);
        gasto.setMonto(monto);
        gasto.setFecha(fecha);
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);

        // Nueva lógica de reparto
        BigDecimal montoPorPersona = monto.divide(
            BigDecimal.valueOf(nuevosParticipantes.size()),
            2, // escala de 2 decimales
            BigDecimal.ROUND_HALF_UP // redondeo hacia arriba
        );

        for (Long idMiembro : nuevosParticipantes) {
            Miembro participante = miembroRepository.findById(idMiembro)
                    .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

            if (!participante.getId().equals(idPagador)) {
                Deuda deuda = new Deuda();
                deuda.setGasto(gasto);
                deuda.setDeudor(participante);
                deuda.setMonto(montoPorPersona);
                deudaRepository.save(deuda);

                participante.setSaldo(participante.getSaldo().subtract(montoPorPersona));
                miembroRepository.save(participante);
            }
        }

        BigDecimal totalRecuperado = montoPorPersona.multiply(BigDecimal.valueOf(nuevosParticipantes.size() - 1));
        pagador.setSaldo(pagador.getSaldo().add(totalRecuperado));
        miembroRepository.save(pagador);

        gastoRepository.save(gasto);
    }
}
