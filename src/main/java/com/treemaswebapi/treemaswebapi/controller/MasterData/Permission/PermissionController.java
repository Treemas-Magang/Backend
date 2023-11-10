package com.treemaswebapi.treemaswebapi.controller.MasterData.Permission;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Permission.request.PermissionRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Permission.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-data")
public class PermissionController {
    
    private final PermissionService service;

    @PostMapping("/permission-form/add")
    ResponseEntity<Map<String, Object>> permissionAdd(
        @RequestBody PermissionRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.permissionAdd(request);
        return response;  
    }
}
