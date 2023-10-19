package com.treemaswebapi.treemaswebapi.controller.ProfieController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.ProfileService.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;
    
    @PutMapping("/update-profile")
    public ResponseEntity<Map<String, Object>> updateProfile(
        @RequestBody ProfileRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.updateProfile(request);
        return response;    
    }
}
