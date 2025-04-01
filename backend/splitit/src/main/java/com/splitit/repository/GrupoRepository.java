package com.splitit.repository;

import com.splitit.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByMiembros_Usuario_Id(Long idUsuario);
}
