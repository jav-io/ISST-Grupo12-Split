package com.splitit.service;

import com.splitit.dto.GastoDTO;
import com.splitit.dto.GastoConParticipantesDTO;
import com.splitit.dto.ParticipanteDTO;
import com.splitit.model.Deuda;
import com.splitit.model.Gasto;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
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

    // Método para crear gasto a partir de GastoDTO (ya existente)
    public Gasto crearGasto(GastoDTO gastoDTO) {
        if (gastoDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero.");
        }
        // Obtener el grupo
        Grupo grupo = grupoService.buscarPorId(gastoDTO.getIdGrupo());
        // Obtener el miembro pagador
        Miembro pagador = miembroService.buscarPorId(gastoDTO.getIdPagador());
        // Crear la entidad Gasto
        Gasto gasto = new Gasto();
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setCategoria(gastoDTO.getCategoria());
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);
        gasto.setFecha(new Date());
        // Inicializamos la lista de deudas si no está ya inicializada
        if (gasto.getDeudas() == null) {
            gasto.setDeudas(new ArrayList<>());
        }
        Gasto gastoGuardado = gastoRepository.save(gasto);
        
        // Calcular el monto por participante
        List<Long> participantesIds = gastoDTO.getIdParticipantes();
        if (participantesIds.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un participante para compartir el gasto.");
        }
        float montoPorParticipante = gastoDTO.getMonto() / participantesIds.size();
        
        // Crear deudas para cada participante
        for (Long idParticipante : participantesIds) {
            Miembro participante = miembroService.buscarPorId(idParticipante);
            // Se asume que la clase Deuda está correctamente implementada
            // y que la relación cascade en Gasto se encarga de persistirla.
            var deuda = new com.splitit.model.Deuda();
            deuda.setMonto(montoPorParticipante);
            deuda.setDeudor(participante);
            deuda.setGasto(gastoGuardado);
            gastoGuardado.getDeudas().add(deuda);
        }
        
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
    
    // Nuevo método para obtener gastos con participantes extendidos
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
            
            // Obtener información del pagador
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
