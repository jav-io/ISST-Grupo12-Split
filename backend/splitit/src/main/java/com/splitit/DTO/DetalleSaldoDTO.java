// backend/splitit/src/main/java/com/splitit/dto/DetalleSaldoDTO.java
package com.splitit.dto;

public class DetalleSaldoDTO {
    private String nombreUsuario;
    private float cantidad;

    public DetalleSaldoDTO(String nombreUsuario, float cantidad) {
        this.nombreUsuario = nombreUsuario;
        this.cantidad = cantidad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public float getCantidad() {
        return cantidad;
    }
}
