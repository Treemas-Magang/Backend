package com.treemaswebapi.treemaswebapi.service;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;

public interface UserService {
    
    void registerUser(UserEntity user);
    void loginUser(UserEntity user);
    
}
