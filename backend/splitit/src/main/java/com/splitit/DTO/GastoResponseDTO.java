package com.splitit.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GastoResponseDTO {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private String pagadorNombre;
    private String grupoNombre;
    private String categoria;

    // Constructor vacío
    public GastoResponseDTO() {}

    // Constructor con todos los campos
    public GastoResponseDTO(Long id, String descripcion, BigDecimal monto, LocalDateTime fecha,
                          String pagadorNombre, String grupoNombre, String categoria) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
        this.pagadorNombre = pagadorNombre;
        this.grupoNombre = grupoNombre;
        this.categoria = categoria;
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

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
