package com.pbl.pbl_be.dto;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserDTO user;
}