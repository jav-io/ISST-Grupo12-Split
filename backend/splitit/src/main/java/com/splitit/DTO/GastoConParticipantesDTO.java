package com.splitit.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GastoConParticipantesDTO {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private String pagadorNombre;
    private Long pagadorId;
    private String grupoNombre;
    private Long grupoId;
    private String categoria;
    private List<ParticipanteDTO> participantes;

    // Constructor vacío
    public GastoConParticipantesDTO() {}

    // Constructor con todos los campos
    public GastoConParticipantesDTO(Long id, String descripcion, BigDecimal monto, LocalDateTime fecha,
                                   String pagadorNombre, Long pagadorId, String grupoNombre, Long grupoId,
                                   String categoria, List<ParticipanteDTO> participantes) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
        this.pagadorNombre = pagadorNombre;
        this.pagadorId = pagadorId;
        this.grupoNombre = grupoNombre;
        this.grupoId = grupoId;
        this.categoria = categoria;
        this.participantes = participantes;
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

    public String getPagadorNombre() {
        return pagadorNombre;
    }

    public void setPagadorNombre(String pagadorNombre) {
        this.pagadorNombre = pagadorNombre;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
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

    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteDTO> participantes) {
        this.participantes = participantes;
    }
}

