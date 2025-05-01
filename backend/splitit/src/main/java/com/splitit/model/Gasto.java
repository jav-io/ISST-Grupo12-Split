// backend/splitit/src/main/java/com/splitit/model/Gasto.java
package com.splitit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa un gasto registrado en un grupo
 * Un gasto es pagado por un miembro y puede generar deudas para otros miembros
 */
@Entity
@Table(name = "gasto")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal monto;

    private LocalDateTime fecha;

    private String descripcion;

    private String categoria;

    @Lob
    private byte[] comprobante;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "pagador_id")
    private Miembro pagador;

    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL)
    private List<Deuda> deudas;

    public Gasto() {
        this.fecha = LocalDateTime.now();
        this.deudas = new ArrayList<>();
    }

    public Gasto(BigDecimal monto, String descripcion, String categoria, Grupo grupo, Miembro pagador) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.grupo = grupo;
        this.pagador = pagador;
        this.fecha = LocalDateTime.now();
        this.deudas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public byte[] getComprobante() {
        return comprobante;
    }

    public void setComprobante(byte[] comprobante) {
        this.comprobante = comprobante;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Miembro getPagador() {
        return pagador;
    }

    public void setPagador(Miembro pagador) {
        this.pagador = pagador;
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deuda> deudas) {
        this.deudas = deudas;
    }
}
