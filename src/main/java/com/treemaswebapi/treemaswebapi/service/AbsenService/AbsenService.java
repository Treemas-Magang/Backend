package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenService {

    private final JwtService jwtService;
    private final SysUserRepository sysUserRepository;
    private final PenempatanRepository penempatanRepository;
    private final ProjectRepository projectRepository;

    public ResponseEntity<?> getProjectDetails(String tokenWithBearer) {
        try {
            // Check if the token is valid, and extract 'nik' (assuming it's the user ID) from the token.
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                 String nik = jwtService.extractUsername(token);
                // Look for the projectId(s) that the user has based on their 'nik'.
                List<String> projectIds = penempatanRepository.findIdByNik(nik);
                List<String> penempatanUser = penempatanRepository.findAllById(projectIds);
                // Create a list to hold project details.
                List<ProjectDetails> projectDetails = new ArrayList<>();

                // Iterate through each projectId and fetch project details.
                for (String projectId : penempatanUser) {
                    ProjectDetails project = projectRepository.findByProjectId();
                    ProjectDetails projectDetail = new ProjectDetails();
                    projectDetail.setProjectName(project.getName());
                    projectDetail.setProjectAddress(project.getAddress());

                    projectDetails.add(projectDetail);
                // Create a ProjectDetails object to hold the details.
                ProjectDetails projectDetail = new ProjectDetails();
                projectDetail.setProjectName(project.getName());
                projectDetail.setProjectAddress(project.getAddress());

                projectDetails.add(projectDetail);
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
            }
           
            }

            // Create a response object with project details.
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Project details retrieved successfully");
            response.put("data", projectDetails);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Failed to retrieve project details");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Define a class to hold project details
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ProjectDetails {
        private String projectName;
        private String projectAddress;

        // Getters and setters
    }
}
