// backend/splitit/src/main/java/com/splitit/model/Usuario.java
package com.splitit.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Cada usuario puede participar en múltiples grupos como miembro.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    // Identificador único del usuario, se genera automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    // Nombre completo del usuario
    private String nombre;

    // Email del usuario, debe ser único en el sistema
    @Column(unique = true)
    private String email;

    // Contraseña del usuario (se almacenará encriptada)
    private String password;

    // Indica si el usuario tiene una cuenta premium
    private boolean isPremium;

    // Relación con la entidad Miembro: un usuario puede ser miembro de varios grupos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Miembro> miembros;

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor con parámetros básicos
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.isPremium = false;
    }

    // Getters y setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }
}
