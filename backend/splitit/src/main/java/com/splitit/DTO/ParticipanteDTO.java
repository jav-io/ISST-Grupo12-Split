package com.splitit.dto;

public class ParticipanteDTO {
    private Long idUsuario;
    private String nombre;

    public ParticipanteDTO(Long idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }
}
