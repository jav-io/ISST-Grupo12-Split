package com.splitit.service;

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

    // Método para crear (añadir) un gasto
    public Gasto crearGasto(Gasto gasto) {
        if (gasto.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor que cero.");
        }
        // Asigna la fecha actual si no se proporcionó
        if (gasto.getFecha() == null) {
            gasto.setFecha(new Date());
        }
        // Aquí se podría agregar la lógica para calcular las deudas entre miembros.
        return gastoRepository.save(gasto);
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
