package com.splitit.dto;

import java.util.Date;
import java.util.List;

public class GastoResponseDTO {
    private Long idGasto;
    private float monto;
    private Date fecha;
    private String descripcion;
    private String categoria;
    private Long idGrupo;
    private String nombreGrupo;
    private Long idPagador;
    private String nombrePagador;
    private List<DeudorSimpleDTO> deudores;

    public GastoResponseDTO(Long idGasto, float monto, Date fecha, String descripcion, String categoria,
                              Long idGrupo, String nombreGrupo, Long idPagador, String nombrePagador,
                              List<DeudorSimpleDTO> deudores) {
        this.idGasto = idGasto;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.idPagador = idPagador;
        this.nombrePagador = nombrePagador;
        this.deudores = deudores;
    }

    // Getters
    public Long getIdGasto() { return idGasto; }
    public float getMonto() { return monto; }
    public Date getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public Long getIdGrupo() { return idGrupo; }
    public String getNombreGrupo() { return nombreGrupo; }
    public Long getIdPagador() { return idPagador; }
    public String getNombrePagador() { return nombrePagador; }
    public List<DeudorSimpleDTO> getDeudores() { return deudores; }
}
