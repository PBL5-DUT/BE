package com.pbl.pbl_be.controller;

import java.security.Principal;

import com.pbl.pbl_be.dto.JwtAuthRequest;
import com.pbl.pbl_be.dto.JwtAuthResponse;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.exception.ApiException;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.security.CustomUserDetails;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.UserService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        User user = this.userRepo.findByUsername(request.getUsername()).orElse(null);
        assert user != null;
        String token = this.jwtTokenHelper.generateToken(userDetails,user.getUserId());

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        response.setUser(this.mapper.map(customUserDetails.getUser(), UserDTO.class));

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid username or password !!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = this.userService.registerNewUser(userDTO);
        return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getUser(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        User user = this.userRepo.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.mapper.map(user, UserDTO.class), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            jwtTokenHelper.invalidateToken(token);
        }
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }
}
