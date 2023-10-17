package com.treemaswebapi.treemaswebapi.controller.AuthController;

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
import com.treemaswebapi.treemaswebapi.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    // @PostMapping("/login")
    // public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
    //     // Find the user by username
    //     UserEntity userEntity = userRepository.findByNik(loginRequest.getNik());
    
    //     ApiResponse response;
    
    //     if (userEntity != null && passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
    //         String deviceIdDb = userEntity.getDeviceId();
    //         String deviceIdInpt = loginRequest.getDeviceId();
    //         if (deviceIdDb == null) {
    //             // If deviceId is null in the database, store it
    //             deviceIdDb = loginRequest.getDeviceId();
    //             userEntity.setDeviceId(deviceIdDb);
    //             userRepository.save(userEntity);
    //         } else if (!deviceIdDb.equals(deviceIdInpt)) {
    //             response = new ApiResponse(false, "Device ID mismatch", null, null, 0);
    //             return ResponseEntity.ok(response);
    //         }
    
    //         // Create list of Maps
    //         List<Map<String, String>> dataList = new ArrayList<>();
    //         // Create a Map for the data
    //         Map<String, String> userData = new HashMap<>();
    //         userData.put("token", token);
    //         userData.put("namaKaryawan", userEntity.getNamaKaryawan());
    //         userData.put("nik", userEntity.getNik());
    //         userData.put("deviceIdDb", deviceIdDb);
    
    //         // Adding user data map to the list
    //         dataList.add(userData);
    
    //         // Create the ApiResponse object with the modified format
    //         response = new ApiResponse(true, "Login successful", dataList, dataList, userRepository.countByNik(userEntity.getNik()));
    //     } else {
    //         // User not found or passwords do not match
    //         response = new ApiResponse(false, "Invalid username or password", null, null, 0);
    //     }
    
    //     return ResponseEntity.ok(response); // Return ResponseEntity with ApiResponse
    // }
    

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
     
    }
}
