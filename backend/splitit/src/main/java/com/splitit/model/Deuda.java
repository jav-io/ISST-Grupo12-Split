// backend/splitit/src/main/java/com/splitit/model/Deuda.java
package com.splitit.model;

import jakarta.persistence.*;

/**
 * Entidad que representa la deuda de un miembro hacia un gasto.
 * Es decir, cuánto debe un miembro por haber participado en un gasto.
 */
@Entity
@Table(name = "deuda")
public class Deuda {

    // Identificador único de la deuda
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeuda;

    // Monto que debe el miembro
    private float monto;

    // El miembro que debe pagar esta deuda
    @ManyToOne
    @JoinColumn(name = "idDeudor")
    private Miembro deudor;

    // El gasto al que pertenece esta deuda
    @ManyToOne
    @JoinColumn(name = "idGasto")
    private Gasto gasto;

    // Constructor vacío
    public Deuda() {}

    // Constructor con parámetros
    public Deuda(float monto, Miembro deudor, Gasto gasto) {
        this.monto = monto;
        this.deudor = deudor;
        this.gasto = gasto;
    }

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
