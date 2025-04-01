package com.splitit.dto;

public class SaldoGrupoDTO {
    private Long idUsuario;
    private String nombre;
    private float saldoActual;

    public SaldoGrupoDTO(Long idUsuario, String nombre, float saldoActual) {
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

    public float getSaldoActual() {
        return saldoActual;
    }
}
