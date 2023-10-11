package com.treemaswebapi.treemaswebapi.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.service.JwtService;
import com.treemaswebapi.treemaswebapi.service.UserService;

import io.jsonwebtoken.Claims;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Override
    public void registerUser(UserEntity user) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPassword = bcrypt.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        System.out.println("User Registered");
    }
    
    public boolean isTokenValid(String token) {
    try {
        JwtService jwtService = new JwtService();
        Claims claims = jwtService.validateTokenAndGetClaims(token);
        // If no exception is thrown, the token is valid
        return true;
    } catch (IllegalArgumentException e) {
        // Token validation failed, indicating that the token is invalid
        return false;
    }
    }

    public void loginUser(UserEntity user) {
        String nik = user.getNik();
        String password = user.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        Optional<UserEntity> existingUser = userRepository.findById(nik);
    
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found.");
        }
        UserEntity userEntity = existingUser.get();
        if (passwordEncoder.matches(password, userEntity.getPassword())){
        Optional<UserEntity> userFromDatabase = userRepository.findById(nik);
        BCryptPasswordEncoder mec = new BCryptPasswordEncoder();
        if (userFromDatabase.isPresent()){
            String storedHashedPassword = userEntity.getPassword();

            String providedPassword = user.getPassword();
            
            if (mec.matches(providedPassword, storedHashedPassword)){
                System.out.println("Login successful for user with Nik: " + nik);
            }else{
                System.out.println("Login failed for user with Nik: " + nik);
            }
        }else{
            System.out.println("User with Nik: " + nik + " not found");
        }
    }
        
}
}
