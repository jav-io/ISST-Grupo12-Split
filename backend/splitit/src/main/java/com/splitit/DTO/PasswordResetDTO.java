// backend/splitit/src/main/java/com/splitit/DTO/PasswordResetDTO.java
package com.splitit.dto;

public class PasswordResetDTO {
    private String email;
    private String token;
    private String newPassword;

    public PasswordResetDTO() {}

    public PasswordResetDTO(String email, String token, String newPassword) {
        this.email = email;
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
