package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.config.AppConstants;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.exception.ResourceNotFoundException;
import com.pbl.pbl_be.model.Role;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.RoleRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder; // Giữ nguyên import
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private ModelMapper mm;


    @Override
    @CacheEvict(value = "allUsers", allEntries = true) // Xóa cache tất cả người dùng khi có người dùng mới đăng ký
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user = this.mm.map(userDTO, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        Role role = this.roleRepo.findByRoleName(Role.RoleName.vlt)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", AppConstants.vlt));

        user.getRoles().add(role);
        user.setCreatedAt(LocalDateTime.now());
        User newUser = this.userRepo.save(user);
        return this.userToDto(newUser);
    }


    @Override

    @CachePut(value = "users", key = "#userDTO.userId") // Cập nhật cache cho người dùng cụ thể
    @CacheEvict(value = "allUsers", allEntries = true) // Xóa cache tất cả người dùng khi có cập nhật
    public UserDTO updateUser(UserDTO userDTO) {
        int userId = userDTO.getUserId();
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatarFilepath(userDTO.getAvatarFilepath());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        }

        return this.userToDto(userRepo.save(user));
    }

    @Override

    @Cacheable(value = "users", key = "#userId") // Cache người dùng theo ID
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.userToDto(user);
    }

    @Override
    @Cacheable(value = "allUsers") // Cache tất cả người dùng
    public List<UserDTO> getAllUser() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override

    @CacheEvict(value = {"users", "allUsers"}, key = "#userId", allEntries = false) // Xóa người dùng cụ thể và cập nhật cache tổng

    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        this.userRepo.delete(user);
    }

    private UserDTO userToDto(User user) {
        return this.mm.map(user, UserDTO.class);
    }
}