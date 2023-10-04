package com.treemaswebapi.treemaswebapi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Override
    public void registerUser(UserEntity user) {
        userRepository.save(user);
        System.out.println("User Registered");
    }

    public void loginUser(UserEntity user) {
        String nik = user.getNik();
        String password = user.getPassword();

        System.out.println("Nik : "+nik);
        System.out.println("Password : "+password);
    }
}
