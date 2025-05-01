package com.splitit.repository;

import com.splitit.model.Miembro;
import com.splitit.model.Usuario;
import com.splitit.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    Optional<Miembro> findByUsuarioAndGrupo(Usuario usuario, Grupo grupo);
}
