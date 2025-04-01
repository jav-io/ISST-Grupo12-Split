package com.splitit.service;

import com.splitit.model.Deuda;
import com.splitit.repository.DeudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    public Deuda crearDeuda(Deuda deuda) {
        return deudaRepository.save(deuda);
    }

    public List<Deuda> obtenerTodas() {
        return deudaRepository.findAll();
    }

    public Deuda buscarPorId(Long id) {
        return deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));
    }

    public void eliminarDeuda(Long id) {
        deudaRepository.deleteById(id);
    }
}
