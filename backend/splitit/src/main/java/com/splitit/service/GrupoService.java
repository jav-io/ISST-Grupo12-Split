package com.splitit.service;

import com.splitit.model.Grupo;
import com.splitit.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo crearGrupo(Grupo grupo) {
        // Validación básica: el nombre es obligatorio
        if (grupo.getNombre() == null || grupo.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del grupo es obligatorio.");
        }
        // Asignar la fecha de creación automáticamente
        grupo.setFechaCreacion(new Date());
        return grupoRepository.save(grupo);
    }

    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    }
}
