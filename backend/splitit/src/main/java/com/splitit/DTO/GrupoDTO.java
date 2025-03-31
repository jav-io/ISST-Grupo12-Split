package com.splitit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GrupoDTO {
    
    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El id del creador es obligatorio")
    private Long idCreador;

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
}
