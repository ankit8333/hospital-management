package com.hospital.dto;

import com.hospital.model.User;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank @Size(min = 3, max = 50)
    private String username;
    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @Email @NotBlank
    private String email;
    private User.Role role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
}
