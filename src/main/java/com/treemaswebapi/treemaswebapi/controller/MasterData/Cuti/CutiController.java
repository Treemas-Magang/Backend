package com.treemaswebapi.treemaswebapi.controller.MasterData.Cuti;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Cuti.request.MasterCutiRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Cuti.CutiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master-data")
@RequiredArgsConstructor
public class CutiController {
    
    private final CutiService service;

    @PostMapping("/cuti-form/add")
    ResponseEntity<Map<String, Object>> cutiAdd(
        @RequestBody MasterCutiRequest request,
        @RequestHeader("Authorization") String jwtToken
    ) {
        ResponseEntity<Map<String, Object>> response = service.cutiAdd(request, jwtToken);
        return response;  
    }
}
