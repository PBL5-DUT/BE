package com.pbl.pbl_be.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer userId;
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private String password;

    private String fullName;
    private String phone;
    private String address;
    private String avatarFilepath;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RoleDTO> roles;
}
