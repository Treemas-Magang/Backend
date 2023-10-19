package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.controller.AbsenController.ProjectListRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
import com.treemaswebapi.treemaswebapi.repository.ProjectMasterRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenService {
    private final ProjectMasterRepo projectRepo;    

    public ResponseEntity <List<ProjectMaster>> projectList (ProjectListRequest request) {
    try{
            List<ProjectMaster> projects = projectRepo.findAll(); // Retrieve all projects from the database
            return ResponseEntity.ok(projects);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}