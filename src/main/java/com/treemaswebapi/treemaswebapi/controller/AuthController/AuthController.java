package com.treemaswebapi.treemaswebapi.controller.AuthController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.AuthService.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest request) {
        ResponseEntity<Map<String, String>> response = service.register(request);
        return response;    
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(
        @RequestBody LoginRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.login(request);
        return response;
    }
}
