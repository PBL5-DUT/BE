package com.pbl.pbl_be.service;


import com.pbl.pbl_be.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser();
    UserDTO getUserById(Integer userId);
    UserDTO updateUser(UserDTO user);
    void deleteUser(Integer userId);
    UserDTO registerNewUser(UserDTO user);
}