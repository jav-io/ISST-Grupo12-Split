package com.splitit.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class GrupoDTO {

    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El id del creador es obligatorio")
    private Long idCreador;

    private Date fechaCreacion;

    private List<ParticipanteDTO> miembros;

    // ✅ NUEVO: lista de miembros a crear (nombre + email)
    private List<NuevoMiembroDTO> nuevosMiembros;

    public static class NuevoMiembroDTO {
        private String nombre;
        private String email;

        public NuevoMiembroDTO() {}

        public NuevoMiembroDTO(String nombre, String email) {
            this.nombre = nombre;
            this.email = email;
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

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(Long idCreador) {
        this.idCreador = idCreador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ParticipanteDTO> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<ParticipanteDTO> miembros) {
        this.miembros = miembros;
    }

    public List<NuevoMiembroDTO> getNuevosMiembros() {
        return nuevosMiembros;
    }

    public void setNuevosMiembros(List<NuevoMiembroDTO> nuevosMiembros) {
        this.nuevosMiembros = nuevosMiembros;
    }
}
