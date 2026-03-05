package com.example.Event_Management.Dto;

import com.example.Event_Management.Utils.AppUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDto {
    public Long id;
    @NotBlank
    public String userName;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password must be at least 8 characters long and contain letters, digits, and special characters")
    public String password;
     @NotBlank
    public String email;
     @NotNull
    public AppUtils.Roles role;

     @NotNull
    public Long contactNumber;

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppUtils.Roles getRole() {
        return role;
    }

    public void setRole(AppUtils.Roles role) {
        this.role = role;
    }
}
