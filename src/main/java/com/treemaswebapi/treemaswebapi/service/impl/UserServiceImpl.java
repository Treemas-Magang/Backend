package com.treemaswebapi.treemaswebapi.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.service.UserService;

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
    }
    

    public void loginUser(UserEntity user) {
        String nik = user.getNik();
        Optional<UserEntity> userFromDatabase = userRepository.findById(nik);
        BCryptPasswordEncoder mec = new BCryptPasswordEncoder();
        if (userFromDatabase.isPresent()){
            UserEntity userEntity = userFromDatabase.get();
            String storedHashedPassword = userEntity.getPassword();
            String providedPassword = user.getPassword();
            
            if (mec.matches(providedPassword, storedHashedPassword)){
                
            }else{
                System.out.println("Login failed for user with Nik: " + nik);
            }
        }else{
            System.out.println("User with Nik: " + nik + " not found");
        }
    }
        
}

