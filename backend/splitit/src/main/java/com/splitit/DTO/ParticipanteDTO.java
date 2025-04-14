package com.splitit.dto;
import com.splitit.dto.ParticipanteDTO;


public class ParticipanteDTO {

    private String nombre;
    private String email;

    public ParticipanteDTO() {}

    public ParticipanteDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Añade este constructor también:
    public ParticipanteDTO(Long id, String nombre) {
        this.nombre = nombre;
        this.email = ""; // o puedes crear también un campo id si realmente necesitas el ID
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
}


