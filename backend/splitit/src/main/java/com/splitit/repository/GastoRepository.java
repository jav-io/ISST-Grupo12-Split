package com.splitit.repository;

import com.splitit.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // Métodos adicionales si fueran necesarios
}