package com.splitit.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class GastoDTO {

    @NotNull(message = "El monto es obligatorio")
    private Float monto;

    private String descripcion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private String categoria;

    @NotNull(message = "El id del grupo es obligatorio")
    private Long idGrupo;

    @NotNull(message = "El id del pagador es obligatorio")
    private Long idPagador;

    @NotNull(message = "La lista de participantes es obligatoria")
    private List<Long> idParticipantes;

    // Getters y Setters
    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Long getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Long idGrupo) { this.idGrupo = idGrupo; }

    public Long getIdPagador() { return idPagador; }
    public void setIdPagador(Long idPagador) { this.idPagador = idPagador; }

    public List<Long> getIdParticipantes() { return idParticipantes; }
    public void setIdParticipantes(List<Long> idParticipantes) { this.idParticipantes = idParticipantes; }
}
