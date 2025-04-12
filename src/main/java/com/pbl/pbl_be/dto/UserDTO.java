package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO
{

    private Integer user_id;
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private String password;

    private String full_name;
    private String phone;
    private String address;
    private String avatar_filepath;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Set<Role> roles = new HashSet<>();
}
