package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public enum RoleName {
        vlt, pm, admin
    }

    public String getRoleName() {
        return roleName.name();
    }
}
