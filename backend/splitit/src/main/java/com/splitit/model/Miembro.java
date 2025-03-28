// backend/splitit/src/main/java/com/splitit/model/Miembro.java
package com.splitit.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa la relación entre un Usuario y un Grupo
 * Un miembro es un usuario que pertenece a un grupo específico
 */
@Entity
@Table(name = "miembro")
public class Miembro {
    # Identificador único del miembro, se genera automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMiembro;
    
    # Saldo actual del miembro en el grupo (positivo si debe recibir, negativo si debe pagar)
    private float saldoActual;
    
    # Rol del miembro en el grupo (ej: "ADMIN", "MEMBER")
    private String rolEnGrupo;
    
    # Relación con la entidad Usuario: cada miembro está asociado a un usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    # Relación con la entidad Grupo: cada miembro pertenece a un grupo
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;
    
    # Relación con la entidad Gasto: gastos pagados por este miembro
    @OneToMany(mappedBy = "pagador")
    private List<Gasto> gastosPagados;
    
    # Relación con la entidad Deuda: deudas pendientes de este miembro
    @OneToMany(mappedBy = "deudor")
    private List<Deuda> deudasPendientes;
    
    # Constructor vacío que inicializa el saldo a cero
    public Miembro() {
        this.saldoActual = 0;
    }
    
    # Constructor con parámetros básicos
    public Miembro(Usuario usuario, Grupo grupo, String rolEnGrupo) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.rolEnGrupo = rolEnGrupo;
        this.saldoActual = 0; # Inicialmente el saldo es cero
    }
    
    # Getters y setters
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
