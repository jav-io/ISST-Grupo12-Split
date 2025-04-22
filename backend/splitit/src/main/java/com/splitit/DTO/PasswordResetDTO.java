// backend/splitit/src/main/java/com/splitit/dto/PasswordResetDTO.java
package com.splitit.dto;

public class PasswordResetDTO {
    private String email;
    private String nuevaPassword;

    public PasswordResetDTO() {}

    public PasswordResetDTO(String email, String nuevaPassword) {
        this.email = email;
        this.nuevaPassword = nuevaPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }
}
