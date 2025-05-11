package com.splitit.dto;

import java.math.BigDecimal;

public class ParticipanteDTO {
    private Long id;
    private String nombre;
    private String email;
    private BigDecimal montoAdeudado;

    // Constructor vacío
    public ParticipanteDTO() {}

    // Constructor con todos los campos
    public ParticipanteDTO(Long id, String nombre, String email, BigDecimal montoAdeudado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.montoAdeudado = montoAdeudado;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMontoAdeudado() {
        return montoAdeudado;
    }

    public void setMontoAdeudado(BigDecimal montoAdeudado) {
        this.montoAdeudado = montoAdeudado;
    }
}


