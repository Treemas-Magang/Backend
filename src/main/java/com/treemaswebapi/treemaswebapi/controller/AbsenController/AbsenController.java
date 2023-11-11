package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;

@RestController
@RequestMapping("/api/absen")
public class AbsenController {

    private final AbsenService absenService;

    public AbsenController(AbsenService absenService) {
        this.absenService = absenService;
    }

    @GetMapping("/project-list")
    public ResponseEntity<Map<String, Object>> getProjectDetails(
        @RequestHeader("Authorization") String token
    ) {
        return absenService.getProjectDetails(token);
    }

    @PostMapping("/update-project")
    public ResponseEntity<Map<String, Object>> updateProject(
        @RequestHeader("Authorization") String token,
        @RequestBody ProjectEntity projectId
    ) {
        return absenService.updateProject(token, projectId);
    }

    @PostMapping("/input-absen")
    public ResponseEntity<Map<String, Object>> inputAbsen(
        @RequestHeader("Authorization") String token,
        @RequestBody AbsenRequest request
    ) {
        return absenService.inputAbsen(token, request);
    }

    @PostMapping("/input-absen-pulang")
    public ResponseEntity<Map<String, Object>> inputAbsenPulang(
        @RequestHeader("Authorization") String token,
        @RequestBody AbsenRequest request
    ) {
        return absenService.inputAbsenPulang(token, request);
    }
}