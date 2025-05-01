// backend/splitit/src/main/java/com/splitit/DTO/DetalleSaldoDTO.java
package com.splitit.DTO;

import java.math.BigDecimal;

public class DetalleSaldoDTO {
    private Long idUsuario;
    private String nombre;
    private BigDecimal saldo;

    public DetalleSaldoDTO(Long idUsuario, String nombre, BigDecimal saldo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
