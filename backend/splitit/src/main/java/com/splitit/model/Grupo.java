// backend/splitit/src/main/java/com/splitit/model/Grupo.java
package com.splitit.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa un grupo de gastos compartidos.
 * Un grupo contiene múltiples miembros y gastos.
 */
@Entity
@Table(name = "grupo")
public class Grupo {

    // Identificador único del grupo, se genera automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    // Nombre del grupo
    private String nombre;

    // Descripción opcional del grupo
    private String descripcion;

    // Fecha de creación del grupo
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    // Relación con la entidad Miembro: un grupo tiene múltiples miembros
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Miembro> miembros;

    // Relación con la entidad Gasto: un grupo tiene múltiples gastos
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Gasto> gastos;

    // Constructor vacío que inicializa la fecha de creación
    public Grupo() {
        this.fechaCreacion = new Date();
    }

    // Constructor con parámetros básicos
    public Grupo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = new Date();
    }

    // Getters y setters
    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }
}
