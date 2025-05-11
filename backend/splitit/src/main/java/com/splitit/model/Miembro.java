package com.splitit.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "miembro")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @Column(name = "saldo_actual", precision = 10, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    private String rolEnGrupo;

    @OneToMany(mappedBy = "pagador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gasto> gastosPagados;

    @OneToMany(mappedBy = "deudor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deuda> deudasPendientes;

    public Miembro() {
        this.saldo = BigDecimal.ZERO;
    }

    public Miembro(Usuario usuario, Grupo grupo, String rolEnGrupo) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.rolEnGrupo = rolEnGrupo;
        this.saldo = BigDecimal.ZERO;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    // Métodos de compatibilidad para código existente
    public Long getIdMiembro() {
        return id;
    }

    public void setIdMiembro(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldoActual() {
        return saldo;
    }

    public void setSaldoActual(float saldo) {
        this.saldo = BigDecimal.valueOf(saldo);
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
