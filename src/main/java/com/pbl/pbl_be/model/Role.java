package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName role_name;

    public enum RoleName {
        vlt, pm, admin
    }

    public String getRoleName() {
        return role_name.name();
    }
}


