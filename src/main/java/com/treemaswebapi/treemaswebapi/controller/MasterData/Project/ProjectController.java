package com.treemaswebapi.treemaswebapi.controller.MasterData.Project;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Permission.request.PermissionRequest;
import com.treemaswebapi.treemaswebapi.controller.MasterData.Project.request.ProjectRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Project.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-data")
public class ProjectController {
    
    private final ProjectService service;

    @PostMapping("/project-form/add")
    ResponseEntity<Map<String, Object>> projectAdd(
        @RequestBody ProjectRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.projectAdd(request);
        return response;  
    }

    @GetMapping("/project-view")
    ResponseEntity<Map<String, Object>> projectGet() {
        ResponseEntity<Map<String, Object>> response = service.projectGet();
        return response;  
    }

}
