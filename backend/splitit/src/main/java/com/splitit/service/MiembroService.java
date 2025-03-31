package com.splitit.service;

import com.splitit.model.Miembro;
import com.splitit.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    public Miembro crearMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    public List<Miembro> obtenerTodos() {
        return miembroRepository.findAll();
    }

    public Miembro buscarPorId(Long id) {
        return miembroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
    }

    // Aquí podrías añadir métodos para actualizar o eliminar miembros si es necesario.
}