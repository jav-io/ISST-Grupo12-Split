package com.splitit.dto;

import java.math.BigDecimal;

public class DeudorSimpleDTO {
    private Long idUsuario;
    private String nombre;
    private BigDecimal monto;

    public DeudorSimpleDTO(Long idUsuario, String nombre, BigDecimal monto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.monto = monto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
