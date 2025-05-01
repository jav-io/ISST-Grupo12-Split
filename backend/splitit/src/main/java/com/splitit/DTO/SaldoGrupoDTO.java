package com.splitit.DTO;

import java.math.BigDecimal;

public class SaldoGrupoDTO {
    private Long idUsuario;
    private String nombre;
    private BigDecimal saldoActual;

    public SaldoGrupoDTO(Long idUsuario, String nombre, BigDecimal saldoActual) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.saldoActual = saldoActual;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }
}
