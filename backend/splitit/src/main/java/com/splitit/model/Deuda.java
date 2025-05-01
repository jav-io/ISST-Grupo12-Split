package com.splitit.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deuda")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDeuda")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeuda;

    @Column(precision = 10, scale = 2)
    private BigDecimal monto;

    private boolean saldada = false;

    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "idMiembro")
    private Miembro deudor;

    @ManyToOne
    @JoinColumn(name = "idGasto")
    private Gasto gasto;

    // Constructor vacío
    public Deuda() {
        this.saldada = false;
    }

    // Constructor con campos
    public Deuda(BigDecimal monto, Gasto gasto, Miembro deudor) {
        this.monto = monto;
        this.gasto = gasto;
        this.deudor = deudor;
        this.saldada = false;
    }

    // Getters y setters
    public Long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public boolean isSaldada() {
        return saldada;
    }

    public void setSaldada(boolean saldada) {
        this.saldada = saldada;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Miembro getDeudor() {
        return deudor;
    }

    public void setDeudor(Miembro deudor) {
        this.deudor = deudor;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }
}
