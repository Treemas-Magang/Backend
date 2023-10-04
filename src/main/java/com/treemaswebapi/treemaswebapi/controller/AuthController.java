package com.treemaswebapi.treemaswebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity user) {
        userService.registerUser(user);
        return "Register success!";
        
        
    }
}
