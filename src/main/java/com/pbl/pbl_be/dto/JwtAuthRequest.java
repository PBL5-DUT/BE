package com.pbl.pbl_be.dto;


import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;

}
