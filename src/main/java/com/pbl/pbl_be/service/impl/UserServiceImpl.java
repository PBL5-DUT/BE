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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDTO registerNewUser(UserDTO userDTO) {
        // Chuyển đổi từ DTO sang entity User
        User user = this.mm.map(userDTO, User.class);

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // Lấy vai trò "vlt" từ cơ sở dữ liệu (hoặc bất kỳ vai trò nào bạn muốn gán cho người dùng)
        Role role = this.roleRepo.findByRoleName("vlt")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", AppConstants.vlt));  // Sửa ở đây: Thêm dấu phẩy

        // Thêm vai trò vào người dùng
        user.getRoles().add(role);

        // Lưu người dùng vào cơ sở dữ liệu
        User newUser = this.userRepo.save(user);

        // Trả về UserDTO sau khi lưu
        return this.mm.map(newUser, UserDTO.class);
    }



    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userToDto(userRepo.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFull_name(userDTO.getFull_name());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar_filepath(userDTO.getAvatar_filepath());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        }

        return this.userToDto(userRepo.save(user));
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDTO dto) {
        return this.mm.map(dto, User.class);
    }

    private UserDTO userToDto(User user) {
        return this.mm.map(user, UserDTO.class);
    }
}
