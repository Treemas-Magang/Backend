package com.treemaswebapi.treemaswebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.service.JwtService;
import com.treemaswebapi.treemaswebapi.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
    // Find the user by username (assuming the username is the NIK)
    UserEntity userEntity = userRepository.findByNik(loginRequest.getNik());

    ApiResponse response; // Declare ApiResponse variable here

    if (userEntity != null && passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
        String deviceId = userEntity.getDeviceId();
        if (deviceId == null) {
            // If deviceId is null in the database, store it
            deviceId = loginRequest.getDeviceId();
            userEntity.setDeviceId(deviceId);
            userRepository.save(userEntity);
        }
        
        // Passwords match, generate a JWT token
        String token = jwtService.generateToken(
            userEntity.getNik(),
            userEntity.getNamaKaryawan(),
            deviceId // Use the deviceId obtained from the database or request
        );
        response = new ApiResponse(true, "Login successful", token, userEntity.getNamaKaryawan(), deviceId, userEntity.getNik());
    } else {
        // User not found or passwords do not match
        response = new ApiResponse(false, "Invalid username or password", null, null, null, null);
    }
    
    return ResponseEntity.ok(response); // Return ResponseEntity with ApiResponse
}


    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity user) {
        userService.registerUser(user);
        return "Register success!";

    }


    @GetMapping("/dashboard-mobile")
    public String dashboard() {
        return "Hello";
    }
}
