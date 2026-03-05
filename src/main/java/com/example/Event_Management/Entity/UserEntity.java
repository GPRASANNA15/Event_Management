package com.example.Event_Management.Entity;

import com.example.Event_Management.Utils.AppUtils;
import jakarta.persistence.*;

import java.util.Enumeration;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @Column
    private String userName;
    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private AppUtils.Roles role;

    @Column
    private Long contactNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }
}
