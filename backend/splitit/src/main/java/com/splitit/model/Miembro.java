package com.splitit.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "miembro")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idMiembro")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMiembro;

    private float saldoActual;

    private String rolEnGrupo;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;

    @OneToMany(mappedBy = "pagador")
    private List<Gasto> gastosPagados;

    @OneToMany(mappedBy = "deudor")
    private List<Deuda> deudasPendientes;

    public Miembro() {
        this.saldoActual = 0;
    }

    public Miembro(Usuario usuario, Grupo grupo, String rolEnGrupo) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.rolEnGrupo = rolEnGrupo;
        this.saldoActual = 0;
    }
    // Getters y setters
    public Long getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Long idMiembro) {
        this.idMiembro = idMiembro;
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getRolEnGrupo() {
        return rolEnGrupo;
    }

    public void setRolEnGrupo(String rolEnGrupo) {
        this.rolEnGrupo = rolEnGrupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Gasto> getGastosPagados() {
        return gastosPagados;
    }

    public void setGastosPagados(List<Gasto> gastosPagados) {
        this.gastosPagados = gastosPagados;
    }

    public List<Deuda> getDeudasPendientes() {
        return deudasPendientes;
    }

    public void setDeudasPendientes(List<Deuda> deudasPendientes) {
        this.deudasPendientes = deudasPendientes;
    }
}
