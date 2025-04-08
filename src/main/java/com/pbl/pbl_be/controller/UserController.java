package com.pbl.pbl_be.controller;


import com.pbl.pbl_be.dto.ApiResponse;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Post
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
    {
        UserDTO u = this.userService.createUser(userDTO);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid  @RequestBody UserDTO userDTO,@PathVariable("userId") Integer id)
    {
        UserDTO u = this.userService.updateUser(userDTO,id);
        return ResponseEntity.ok(u);
    }


    //delete
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);

    }

    //get

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


}