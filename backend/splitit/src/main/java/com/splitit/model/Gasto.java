// backend/splitit/src/main/java/com/splitit/model/Gasto.java
package com.splitit.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidad que representa un gasto registrado en un grupo
 * Un gasto es pagado por un miembro y puede generar deudas para otros miembros
 */
@Entity
@Table(name = "gasto")
public class Gasto {
    # Identificador único del gasto, se genera automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGasto;
    
    # Monto total del gasto
    private float monto;
    
    # Fecha en que se realizó el gasto
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    # Descripción del gasto (ej: "Cena", "Supermercado")
    private String descripcion;
    
    # Categoría del gasto (ej: "Comida", "Transporte")
    private String categoria;
    
    # Imagen del comprobante/ticket (opcional)
    @Lob
    private byte[] comprobante;
    
    # Relación con la entidad Grupo: cada gasto pertenece a un grupo
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;
    
    # Relación con la entidad Miembro: cada gasto es pagado por un miembro
    @ManyToOne
    @JoinColumn(name = "idPagador")
    private Miembro pagador;
    
    # Relación con la entidad Deuda: un gasto genera múltiples deudas
    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL)
    private List<Deuda> deudas;
    
    # Constructor vacío que inicializa la fecha actual
    public Gasto() {
        this.fecha = new Date();
    }
    
    # Constructor con parámetros básicos
    public Gasto(float monto, String descripcion, String categoria, Grupo grupo, Miembro pagador) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.grupo = grupo;
        this.pagador = pagador;
        this.fecha = new Date(); # Establece la fecha actual
    }
    
    # Getters y setters
    public Long getIdGasto() {
        return idGasto;
    }
    
    public void setIdGasto(Long idGasto) {
        this.idGasto = idGasto;
    }
    
    public float getMonto() {
        return monto;
    }
    
    public void setMonto(float monto) {
        this.monto = monto;
