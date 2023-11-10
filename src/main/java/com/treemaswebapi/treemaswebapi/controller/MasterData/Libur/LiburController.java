package com.treemaswebapi.treemaswebapi.controller.MasterData.Libur;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Libur.request.LiburRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Libur.LiburService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-data")
public class LiburController {
    
    private final LiburService service;

    @PostMapping("/libur-form/add")
    ResponseEntity<Map<String, Object>> liburAdd(
        @RequestBody LiburRequest request,
        @RequestHeader("Authorization") String jwtToken
    ) {
        ResponseEntity<Map<String, Object>> response = service.liburAdd(request, jwtToken);
        return response;  
    }

    @GetMapping("/libur-view")
    public ResponseEntity<Map<String, Object>> liburGet() {
        ResponseEntity<Map<String,Object>> response = service.liburGet();
        return response;
    }
}
