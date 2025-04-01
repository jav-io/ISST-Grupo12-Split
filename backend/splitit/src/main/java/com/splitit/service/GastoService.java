package com.splitit.service;



import com.splitit.model.Miembro;
import com.splitit.model.Grupo;
import com.splitit.model.Deuda;
import com.splitit.dto.GastoDTO;
import com.splitit.model.Gasto;
import com.splitit.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    


    // Nuevo método: crear gasto a partir de GastoDTO
    public Gasto crearGasto(GastoDTO gastoDTO) {
        if (gastoDTO.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero.");
        }

        // Obtener el grupo
        Grupo grupo = grupoService.buscarPorId(gastoDTO.getIdGrupo());

        // Obtener el miembro pagador (se asume que ya es miembro del grupo)
        Miembro pagador = miembroService.buscarPorId(gastoDTO.getIdPagador());

        // Crear la entidad Gasto
        Gasto gasto = new Gasto();
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setCategoria(gastoDTO.getCategoria());
        gasto.setGrupo(grupo);
        gasto.setPagador(pagador);
        gasto.setFecha(new Date());

         // Guardamos el gasto (sin deudas por ahora)
         Gasto gastoGuardado = gastoRepository.save(gasto);

         // Calcular el monto que debe pagar cada participante
        List<Long> participantesIds = gastoDTO.getIdParticipantes();
        if (participantesIds.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un participante para compartir el gasto.");
        }
        float montoPorParticipante = gastoDTO.getMonto() / participantesIds.size();
        
        // Para cada participante, crear una deuda asociada al gasto
        for (Long idParticipante : participantesIds) {
            Miembro participante = miembroService.buscarPorId(idParticipante);
            Deuda deuda = new Deuda();
            deuda.setMonto(montoPorParticipante);
            deuda.setDeudor(participante);
            deuda.setGasto(gastoGuardado);
            // Se asume que la entidad Gasto tiene una lista de deudas y que está configurada con cascade.
            gastoGuardado.getDeudas().add(deuda);
        }
        // Guardamos nuevamente el gasto con las deudas (la cascada se encargará de persistirlas)
        return gastoRepository.save(gastoGuardado);
    }    

    public List<Gasto> obtenerTodos() {
        return gastoRepository.findAll();
    }

    public Gasto buscarPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado."));
    }

    // Método para editar un gasto existente
    public Gasto editarGasto(Long id, Gasto gastoActualizado) {
        Gasto gastoExistente = buscarPorId(id);
        gastoExistente.setMonto(gastoActualizado.getMonto());
        gastoExistente.setDescripcion(gastoActualizado.getDescripcion());
        gastoExistente.setCategoria(gastoActualizado.getCategoria());
        // Se pueden actualizar otros campos, como comprobante, si es necesario.
        return gastoRepository.save(gastoExistente);
    }
}
