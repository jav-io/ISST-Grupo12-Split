package com.splitit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class InvitacionDTO {

    @NotNull(message = "El id del grupo es obligatorio")
    private Long idGrupo;

    @NotNull(message = "El email del usuario es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    public InvitacionDTO() {
    }

    // Getters y setters
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
