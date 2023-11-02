package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;
import com.treemaswebapi.treemaswebapi.service.AuthService.AuthService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/absen")
public class AbsenController {

    private final AbsenService service;

    @GetMapping("/project-list")
    public ResponseEntity<Map<String, Object>> authenticate(
        @RequestHeader("Authorization") String token
    ) {
        ResponseEntity<Map<String, Object>> response = service.(token);
        return response;
    }
}
