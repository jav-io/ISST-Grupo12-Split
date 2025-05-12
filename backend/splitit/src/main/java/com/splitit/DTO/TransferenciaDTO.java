package com.splitit.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {
    private String deudor;
    private String acreedor;
    private BigDecimal monto;

    public TransferenciaDTO(String deudor, String acreedor, BigDecimal monto) {
        this.deudor = deudor;
        this.acreedor = acreedor;
        this.monto = monto;
    }

    public String getDeudor() {
        return deudor;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
