package com.treemaswebapi.treemaswebapi.controller.ProfieController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {
    
    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Ini secured Profile harus ada token!");
    }
}
