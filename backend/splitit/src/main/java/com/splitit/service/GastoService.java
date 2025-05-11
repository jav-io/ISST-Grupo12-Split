package com.splitit.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.dto.GastoResponseDTO;
import com.splitit.dto.ParticipanteDTO;
import com.splitit.model.Deuda;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GastoRepository;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;

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

    // 
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

    // Guardar el gasto para obtener su ID
    gasto = gastoRepository.save(gasto);

    // Participantes del gasto: los indicados o todos si no se indican
    List<Long> participantesIds = gastoDTO.getParticipantesIds() != null && !gastoDTO.getParticipantesIds().isEmpty()
            ? gastoDTO.getParticipantesIds()
            : grupo.getMiembros().stream().map(Miembro::getId).collect(Collectors.toList());

    int numParticipantes = participantesIds.size();
    if (numParticipantes == 0) {
        throw new RuntimeException("No hay participantes en el gasto");
    }

    BigDecimal montoTotal = gasto.getMonto();
    BigDecimal montoPorPersona = montoTotal.divide(BigDecimal.valueOf(numParticipantes), 2, BigDecimal.ROUND_HALF_UP);

    BigDecimal totalDeuda = BigDecimal.ZERO;

    for (Long idParticipante : participantesIds) {
        Miembro participante = miembroRepository.findById(idParticipante)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        if (!participante.getGrupo().getId().equals(grupo.getId())) {
            throw new RuntimeException("El participante no pertenece al grupo especificado");
        }

        if (!participante.getId().equals(pagador.getId())) {
            // Crear deuda para los que no son el pagador
            Deuda deuda = new Deuda();
            deuda.setGasto(gasto);
            deuda.setDeudor(participante);
            deuda.setMonto(montoPorPersona);
            deuda.setFecha(gasto.getFecha());
            deudaRepository.save(deuda);

            // Descontar saldo del deudor
            participante.setSaldo(participante.getSaldo().subtract(montoPorPersona));
            miembroRepository.save(participante);

            totalDeuda = totalDeuda.add(montoPorPersona);
        }
    }

    // Aumentar el saldo del pagador con la suma total que los demás le deben
    pagador.setSaldo(pagador.getSaldo().add(totalDeuda));
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

    // Revertir saldos y borrar deudas anteriores
    for (Deuda deuda : gasto.getDeudas()) {
        Miembro deudor = deuda.getDeudor();
        deudor.setSaldo(deudor.getSaldo().add(deuda.getMonto()));
        miembroRepository.save(deudor);
    }

    BigDecimal totalDeudaAnterior = gasto.getDeudas().stream()
            .map(Deuda::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Revertir saldo del antiguo pagador
    gasto.getPagador().setSaldo(gasto.getPagador().getSaldo().subtract(totalDeudaAnterior));
    miembroRepository.save(gasto.getPagador());

    deudaRepository.deleteAll(gasto.getDeudas());
    gasto.getDeudas().clear();

    // Actualizar gasto
    gasto.setDescripcion(descripcion);
    gasto.setMonto(monto);
    gasto.setFecha(fecha);
    gasto.setGrupo(grupo);
    gasto.setPagador(pagador);

    // Nueva lógica de reparto
    int numParticipantes = nuevosParticipantes.size();
    if (numParticipantes == 0) {
        throw new RuntimeException("No hay participantes");
    }

    BigDecimal montoPorPersona = monto.divide(BigDecimal.valueOf(numParticipantes), 2, BigDecimal.ROUND_HALF_UP);
    BigDecimal totalDeudaNueva = BigDecimal.ZERO;

    for (Long idMiembro : nuevosParticipantes) {
        Miembro participante = miembroRepository.findById(idMiembro)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        if (!participante.getId().equals(idPagador)) {
            Deuda deuda = new Deuda();
            deuda.setGasto(gasto);
            deuda.setDeudor(participante);
            deuda.setMonto(montoPorPersona);
            deuda.setFecha(fecha);
            deudaRepository.save(deuda);

            participante.setSaldo(participante.getSaldo().subtract(montoPorPersona));
            miembroRepository.save(participante);

            totalDeudaNueva = totalDeudaNueva.add(montoPorPersona);
        }
    }

    // Actualizar saldo del nuevo pagador
    pagador.setSaldo(pagador.getSaldo().add(totalDeudaNueva));
    miembroRepository.save(pagador);

    gastoRepository.save(gasto);
}
@Transactional
public void eliminarGasto(Long id) {
    Gasto gasto = buscarPorId(id);
    for (Deuda deuda : gasto.getDeudas()) {
        Miembro deudor = deuda.getDeudor();
        deudor.setSaldo(deudor.getSaldo().add(deuda.getMonto()));
        miembroRepository.save(deudor);
    }

    BigDecimal totalDeuda = gasto.getDeudas().stream()
        .map(Deuda::getMonto)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    Miembro pagador = gasto.getPagador();
    pagador.setSaldo(pagador.getSaldo().subtract(totalDeuda));
    miembroRepository.save(pagador);

    deudaRepository.deleteAll(gasto.getDeudas());
    gastoRepository.delete(gasto);
}

}
