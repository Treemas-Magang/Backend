package com.treemaswebapi.treemaswebapi.controller.UpdateListProjectController;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.UpdateListProjectService.UpdateListProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UpdateListProjectController {

    private final UpdateListProjectService projectService;

    @GetMapping("/update-list-projects")
    public ResponseEntity<Map<String, Object>> updateListProjects(@RequestHeader String tokenWithBearer, @RequestBody String projectId) {
        Map<String, Object> response = projectService.updateListProject(tokenWithBearer, projectId);
        HttpStatus status = (boolean) response.get("success") ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(response);
    }
}