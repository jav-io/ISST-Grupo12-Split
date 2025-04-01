package com.splitit.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "deuda")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDeuda")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeuda;

    private float monto;

    private boolean saldada = false;

    @Temporal(TemporalType.DATE)
    private Date fecha = new Date();

    @ManyToOne
    @JoinColumn(name = "idMiembro")
    private Miembro deudor;

    @ManyToOne
    @JoinColumn(name = "idGasto")
    private Gasto gasto;

    // Getters y setters
    public Long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public boolean isSaldada() {
        return saldada;
    }

    public void setSaldada(boolean saldada) {
        this.saldada = saldada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
