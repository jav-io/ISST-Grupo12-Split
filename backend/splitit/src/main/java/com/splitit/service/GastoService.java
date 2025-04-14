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
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GastoRepository;
import com.splitit.repository.MiembroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private MiembroRepository miembroRepository;



    public Gasto crearGasto(GastoDTO gastoDTO) {
        // Validar participantes
        if (gastoDTO.getIdParticipantes() == null || gastoDTO.getIdParticipantes().isEmpty()) {
            throw new RuntimeException("Debe haber al menos un participante para compartir el gasto.");
        }
    
        // Obtener grupo y pagador
        Grupo grupo = grupoService.buscarPorId(gastoDTO.getIdGrupo());
        Miembro pagador = grupo.getMiembros().stream()
            .filter(m -> m.getIdMiembro().equals(gastoDTO.getIdPagador()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("El pagador no es miembro del grupo."));
    
        // Crear y guardar gasto
        Gasto gasto = new Gasto();
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setFecha(Date.valueOf(gastoDTO.getFecha()));
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);
        gasto = gastoRepository.save(gasto); // necesario para registrar ID
    
        // Comprobamos si el pagador está entre los participantes
        boolean pagadorParticipa = gastoDTO.getIdParticipantes().contains(pagador.getIdMiembro());
    
        // Cantidad a pagar por participante
        int numParticipantes = gastoDTO.getIdParticipantes().size();
        float montoPorPersona = gastoDTO.getMonto() / numParticipantes;
    
        float totalRecuperado = 0f;
    
        for (Long idMiembro : gastoDTO.getIdParticipantes()) {
            Miembro participante = grupo.getMiembros().stream()
                .filter(m -> m.getIdMiembro().equals(idMiembro))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Participante no pertenece al grupo."));
    
            // Si no es el pagador, se le crea una deuda
            if (!participante.getIdMiembro().equals(pagador.getIdMiembro())) {
                Deuda deuda = new Deuda();
                deuda.setDeudor(participante);
                deuda.setGasto(gasto);
                deuda.setMonto(montoPorPersona);
                deudaRepository.save(deuda);
    
                // Actualizar saldo: debe dinero
                participante.setSaldoActual(participante.getSaldoActual() - montoPorPersona);
                miembroRepository.save(participante);
    
                totalRecuperado += montoPorPersona;
            }
        }
    
        // El pagador recupera solo lo que los demás le deben (no a sí mismo)
        pagador.setSaldoActual(pagador.getSaldoActual() + totalRecuperado);
        miembroRepository.save(pagador);
    
        return gasto;
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
            if (gasto.getPagador() == null || gasto.getPagador().getUsuario() == null) {
                // Si no hay pagador o el usuario del pagador es null, lo ignoramos
                continue;
            }
    
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
    
    public List<Gasto> obtenerGastosPorGrupo(Long idGrupo) {
        return gastoRepository.findByGrupo_IdGrupo(idGrupo);
    }

    public Gasto obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id).orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
    }

    public void actualizarGasto(Gasto gasto) {
        gastoRepository.save(gasto);
    }

    public void editarGastoConParticipantes(Long id, String descripcion, Float monto, java.util.Date fecha,
                                        Long idGrupo, Long idPagador, List<Long> nuevosParticipantes) {

        Gasto gasto = buscarPorId(id);
        Grupo grupo = grupoService.buscarPorId(idGrupo);
        Miembro pagador = grupo.getMiembros().stream()
                .filter(m -> m.getIdMiembro().equals(idPagador))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pagador no válido"));

        // Revertir saldos actuales antes de editar
        for (Deuda deuda : gasto.getDeudas()) {
            Miembro deudor = deuda.getDeudor();
            deudor.setSaldoActual(deudor.getSaldoActual() + deuda.getMonto());
            miembroRepository.save(deudor);
        }

        pagador.setSaldoActual(pagador.getSaldoActual() -
            gasto.getMonto() * (gasto.getDeudas().size() / (float) gasto.getDeudas().size()));
        miembroRepository.save(pagador);

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
        float montoPorPersona = monto / nuevosParticipantes.size();

        for (Long idMiembro : nuevosParticipantes) {
            Miembro participante = grupo.getMiembros().stream()
                    .filter(m -> m.getIdMiembro().equals(idMiembro))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

            if (!participante.getIdMiembro().equals(pagador.getIdMiembro())) {
                Deuda deuda = new Deuda();
                deuda.setGasto(gasto);
                deuda.setDeudor(participante);
                deuda.setMonto(montoPorPersona);
                deudaRepository.save(deuda);

                participante.setSaldoActual(participante.getSaldoActual() - montoPorPersona);
                miembroRepository.save(participante);
            }
        }

        float totalRecuperado = montoPorPersona * (nuevosParticipantes.size() - 1);
        pagador.setSaldoActual(pagador.getSaldoActual() + totalRecuperado);
        miembroRepository.save(pagador);

        gastoRepository.save(gasto);
    }

}
