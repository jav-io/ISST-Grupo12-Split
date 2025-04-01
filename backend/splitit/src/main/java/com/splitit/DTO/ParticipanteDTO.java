package com.splitit.dto;
import com.splitit.dto.ParticipanteDTO;


public class ParticipanteDTO {
    private Long idUsuario;
    private String nombre;

    public ParticipanteDTO() {
        // Constructor vacío necesario para Spring y Thymeleaf
    }

    public ParticipanteDTO(Long idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

