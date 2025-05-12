package com.splitit.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {
    private String deudor;
    private String acreedor;
    private BigDecimal monto;
    private String nombreGrupo;

    public TransferenciaDTO(String deudor, String acreedor, BigDecimal monto, String nombreGrupo) {
        this.deudor = deudor;
        this.acreedor = acreedor;
        this.monto = monto;
        this.nombreGrupo = nombreGrupo;
    }

    // getters
    public String getDeudor() { return deudor; }
    public String getAcreedor() { return acreedor; }
    public BigDecimal getMonto() { return monto; }
    public String getNombreGrupo() { return nombreGrupo; }
}
