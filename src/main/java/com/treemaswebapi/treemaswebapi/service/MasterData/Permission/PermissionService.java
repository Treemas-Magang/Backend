package com.treemaswebapi.treemaswebapi.service.MasterData.Permission;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Permission.request.PermissionRequest;
import com.treemaswebapi.treemaswebapi.entity.PermissionEntity.PermissionEntity;
import com.treemaswebapi.treemaswebapi.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public ResponseEntity<Map<String, Object>> permissionAdd(
        PermissionRequest request        
    ) {
        try {

            var permissionEntity = PermissionEntity.builder()
                .namaPermission(request.getNamaPermission())
            .build();

            permissionRepository.save(permissionEntity);
            
            Map<String, Object> data = new HashMap<>();
            data.put("user", permissionEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Permission Created");
            response.put("data", data);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to Create Permission!");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
