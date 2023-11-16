package com.treemaswebapi.treemaswebapi.controller.DetailData.Absen;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.DetailData.Absen.AbsenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/detail-data")
@RequiredArgsConstructor
public class AbsenController {
    
    private AbsenService service;

    @GetMapping("/absen-view")
    public ResponseEntity<Map<String, Object>> absenGet() {
        ResponseEntity<Map<String, Object>> response = service.absenGet();
        return response;
    }
}
