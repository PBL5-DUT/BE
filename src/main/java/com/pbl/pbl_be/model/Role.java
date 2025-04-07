package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", unique = true)
    private String roleName;
}


