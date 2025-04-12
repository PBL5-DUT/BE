package com.pbl.pbl_be.service;


import com.pbl.pbl_be.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO registerNewUser(UserDTO user);
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user,Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUser();
    void deleteUser(Integer userId);



}