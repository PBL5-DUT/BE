package com.pbl.pbl_be.dto;

import lombok.Data;

@Data
public class RoleDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String roleName;
}