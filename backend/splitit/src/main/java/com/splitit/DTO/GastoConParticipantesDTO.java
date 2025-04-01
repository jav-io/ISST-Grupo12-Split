package com.splitit.dto;

import java.util.Date;
import java.util.List;

public class GastoConParticipantesDTO {
    private Long idGasto;
    private String descripcion;
    private String categoria;
    private Date fecha;
    private float monto;
    private Long idGrupo;
    private Long idPagador;
    private String nombrePagador;
    private List<ParticipanteDTO> participantes;

    public GastoConParticipantesDTO(Long idGasto, String descripcion, String categoria, Date fecha, float monto,
                                    Long idGrupo, Long idPagador, String nombrePagador, List<ParticipanteDTO> participantes) {
        this.idGasto = idGasto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.monto = monto;
        this.idGrupo = idGrupo;
        this.idPagador = idPagador;
        this.nombrePagador = nombrePagador;
        this.participantes = participantes;
    }

    public Long getIdGasto() {
        return idGasto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public Date getFecha() {
        return fecha;
    }

    public float getMonto() {
        return monto;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public Long getIdPagador() {
        return idPagador;
    }

    public String getNombrePagador() {
        return nombrePagador;
    }

    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }
}

