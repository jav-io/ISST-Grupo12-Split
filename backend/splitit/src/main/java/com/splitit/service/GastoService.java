package com.splitit.service;

import com.splitit.dto.DeudorSimpleDTO;
import com.splitit.dto.GastoDTO;
import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.GastoResponseDTO;
import com.splitit.dto.ParticipanteDTO;
import com.splitit.model.Deuda;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private MiembroService miembroService;

    public Gasto crearGasto(GastoDTO gastoDTO) {
        if (gastoDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero.");
        }

        Grupo grupo = grupoService.buscarPorId(gastoDTO.getIdGrupo());
        Miembro pagador = miembroService.buscarPorId(gastoDTO.getIdPagador());

        if (!pagador.getGrupo().getIdGrupo().equals(grupo.getIdGrupo())) {
            throw new RuntimeException("El pagador no pertenece al grupo especificado.");
        }

        Gasto gasto = new Gasto();
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setCategoria(gastoDTO.getCategoria());
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);
        gasto.setFecha(new Date());

        if (gasto.getDeudas() == null) {
            gasto.setDeudas(new ArrayList<>());
        }
        Gasto gastoGuardado = gastoRepository.save(gasto);

        List<Long> participantesIds = gastoDTO.getIdParticipantes();
        if (participantesIds.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un participante para compartir el gasto.");
        }
        float montoPorParticipante = gastoDTO.getMonto() / participantesIds.size();

        for (Long idParticipante : participantesIds) {
            Miembro participante = miembroService.buscarPorId(idParticipante);
            if (!participante.getGrupo().getIdGrupo().equals(grupo.getIdGrupo())) {
                throw new RuntimeException("El participante con id " + idParticipante + " no pertenece al grupo " + grupo.getIdGrupo());
            }
            Deuda deuda = new Deuda();
            deuda.setMonto(montoPorParticipante);
            deuda.setDeudor(participante);
            deuda.setGasto(gastoGuardado);
            gastoGuardado.getDeudas().add(deuda);

            // Actualización de saldo
            if (!participante.getIdMiembro().equals(pagador.getIdMiembro())) {
                participante.setSaldoActual(participante.getSaldoActual() - montoPorParticipante);
                miembroService.actualizarMiembro(participante);
            }        }

        // El pagador recupera lo que ha pagado por otros
        float totalRecupera = montoPorParticipante * (participantesIds.size() - 1);
        pagador.setSaldoActual(pagador.getSaldoActual() + totalRecupera);
        miembroService.actualizarMiembro(pagador);

        return gastoRepository.save(gastoGuardado);
    }

    public List<Gasto> obtenerTodos() {
        return gastoRepository.findAll();
    }

    public Gasto buscarPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado."));
    }

    public Gasto editarGasto(Long id, Gasto gastoActualizado) {
        Gasto gastoExistente = buscarPorId(id);
        gastoExistente.setMonto(gastoActualizado.getMonto());
        gastoExistente.setDescripcion(gastoActualizado.getDescripcion());
        gastoExistente.setCategoria(gastoActualizado.getCategoria());
        return gastoRepository.save(gastoExistente);
    }

    public List<GastoResponseDTO> obtenerGastosResponse() {
        List<Gasto> gastos = gastoRepository.findAll();
        List<GastoResponseDTO> respuesta = new ArrayList<>();

        for (Gasto gasto : gastos) {
            if (gasto.getPagador() == null || gasto.getPagador().getUsuario() == null) {
                System.out.println("Gasto con ID " + gasto.getIdGasto() + " no tiene pagador definido. Se omite.");
                continue;
            }

            List<DeudorSimpleDTO> deudores = gasto.getDeudas().stream()
                    .filter(deuda -> deuda.getDeudor() != null && deuda.getDeudor().getUsuario() != null)
                    .map(deuda -> new DeudorSimpleDTO(
                            deuda.getDeudor().getUsuario().getIdUsuario(),
                            deuda.getDeudor().getUsuario().getNombre()))
                    .distinct()
                    .toList();

            Grupo grupo = gasto.getGrupo();
            Usuario pagador = gasto.getPagador().getUsuario();

            respuesta.add(new GastoResponseDTO(
                    gasto.getIdGasto(),
                    gasto.getMonto(),
                    gasto.getFecha(),
                    gasto.getDescripcion(),
                    gasto.getCategoria(),
                    grupo.getIdGrupo(),
                    grupo.getNombre(),
                    pagador.getIdUsuario(),
                    pagador.getNombre(),
                    deudores
            ));
        }

        return respuesta;
    }

    public List<GastoConParticipantesDTO> obtenerGastosConParticipantes() {
        List<Gasto> gastos = gastoRepository.findAll();
        List<GastoConParticipantesDTO> resultado = new ArrayList<>();

        for (Gasto gasto : gastos) {
            List<ParticipanteDTO> participantes = gasto.getDeudas().stream()
                    .map(deuda -> {
                        var usuario = deuda.getDeudor().getUsuario();
                        return new ParticipanteDTO(usuario.getIdUsuario(), usuario.getNombre());
                    })
                    .distinct()
                    .toList();

            var pagador = gasto.getPagador().getUsuario();
            resultado.add(new GastoConParticipantesDTO(
                    gasto.getIdGasto(),
                    gasto.getDescripcion(),
                    gasto.getCategoria(),
                    gasto.getFecha(),
                    gasto.getMonto(),
                    gasto.getGrupo().getIdGrupo(),
                    pagador.getIdUsuario(),
                    pagador.getNombre(),
                    participantes
            ));
        }

        return resultado;
    }
}
