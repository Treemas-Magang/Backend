package com.treemaswebapi.treemaswebapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.treemaswebapi.treemaswebapi.response.LoginApiResponse;
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
    public ResponseEntity<LoginApiResponse> login(@RequestBody LoginRequest loginRequest) {
        // Find the user by username
        UserEntity userEntity = userRepository.findByNik(loginRequest.getNik());
    
        LoginApiResponse response;
    
        if (userEntity != null && passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            String deviceIdDb = userEntity.getDeviceId();
            String deviceIdInpt = loginRequest.getDeviceId();
            if (deviceIdDb == null) {
                // If deviceId is null in the database, store it
                deviceIdDb = loginRequest.getDeviceId();
                userEntity.setDeviceId(deviceIdDb);
                userRepository.save(userEntity);
            } else if (!deviceIdDb.equals(deviceIdInpt)) {
                response = new LoginApiResponse(false, "Device ID mismatch", null, null, 0);
                return ResponseEntity.ok(response);
            }
    
            // Passwords match, generate a JWT token
            String token = jwtService.generateToken(
                userEntity.getNik(),
                userEntity.getNamaKaryawan(),
                deviceIdDb // Use the deviceId obtained from the database or request
            );
    
            // Validate the token here
            try {
                jwtService.validateTokenAndGetClaims(token);
            } catch (IllegalArgumentException e) {
                response = new LoginApiResponse(false, "Token is invalid", null, null, 0);
                return ResponseEntity.ok(response);
            }
    
            // Create list of Maps
            List<Map<String, String>> dataList = new ArrayList<>();
            // Create a Map for the data
            Map<String, String> userData = new HashMap<>();
            userData.put("token", token);
            userData.put("namaKaryawan", userEntity.getNamaKaryawan());
            userData.put("nik", userEntity.getNik());
            userData.put("deviceIdDb", deviceIdDb);
    
            // Adding user data map to the list
            dataList.add(userData);
    
            // Create the ApiResponse object with the modified format
            response = new LoginApiResponse(true, "Login successful", dataList, dataList, userRepository.countByNik(userEntity.getNik()));
        } else {
            // User not found or passwords do not match
            response = new LoginApiResponse(false, "Invalid username or password", null, null, 0);
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
