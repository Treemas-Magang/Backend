package com.treemaswebapi.treemaswebapi.service.MasterData.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Project.request.ProjectRequest;
import com.treemaswebapi.treemaswebapi.entity.PermissionEntity.PermissionEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ResponseEntity<Map<String, Object>> projectAdd(
        ProjectRequest request
    ) {
        try {
            
            var projectEntity = ProjectEntity.builder()
                .projectId(request.getProjectId())
                .namaProject(request.getNamaProject())
                .noTlpn(request.getNoTlpn())
                .kota(request.getKota())
                .lokasi(request.getLokasi())
                .gpsLatitude(request.getGpsLatitude())
                .gpsLongitude(request.getGpsLongitude())
                .biayaReimburse(request.getBiayaReimburse())
                .jrkMax(request.getJrkMax())
                .jamKerja(request.getJamKerja())
                .jamMasuk(request.getJamMasuk())
                .jamKeluar(request.getJamKeluar())
            .build();

            projectRepository.save(projectEntity);
            Map<String, Object> data = new HashMap<>();
            data.put("user", projectEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Project Created");
            response.put("data", data);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to Create Project!");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> projectGet() {
        try {
            List<ProjectEntity> project = projectRepository.findAll();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", project);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve project");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
