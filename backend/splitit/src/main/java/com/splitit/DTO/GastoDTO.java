package com.splitit.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GastoDTO {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private Long pagadorId;
    private Long grupoId;
    private String categoria;
    private List<Long> participantesIds;

    // Constructor vacío
    public GastoDTO() {}

    // Constructor con todos los campos
    public GastoDTO(Long id, String descripcion, BigDecimal monto, LocalDateTime fecha, 
                    Long pagadorId, Long grupoId, String categoria, List<Long> participantesIds) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
        this.pagadorId = pagadorId;
        this.grupoId = grupoId;
        this.categoria = categoria;
        this.participantesIds = participantesIds;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Long> getParticipantesIds() {
        return participantesIds;
    }

    public void setParticipantesIds(List<Long> participantesIds) {
        this.participantesIds = participantesIds;
    }
}
