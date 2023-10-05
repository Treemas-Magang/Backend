package com.treemaswebapi.treemaswebapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void registerUser(UserEntity user) {
        userRepository.save(user);
        System.out.println("User Registered");
    }


    @Override
    public void loginUser(UserEntity user) {
        String nik = user.getNik();
        String password = user.getPassword();

        
        Optional<UserEntity> existingUser = userRepository.findById(nik);
    
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found.");
        }
        UserEntity userEntity = existingUser.get();
        if (passwordEncoder.matches(password, userEntity.getPassword())){

        }else{
            throw new IllegalArgumentException("Invalid Password.");
        }
    }
}
