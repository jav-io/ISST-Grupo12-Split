package com.splitit.DTO;

import jakarta.validation.constraints.NotNull;

public class InvitacionDTO {

    @NotNull(message = "El id del grupo es obligatorio")
    private Long idGrupo;
    
    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    public InvitacionDTO() {
    }

    // Getters y setters
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
