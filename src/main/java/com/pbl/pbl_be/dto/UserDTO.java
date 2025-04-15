package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO
{

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
