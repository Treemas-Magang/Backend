package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectDetails;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PenempatanRepository penempatanRepository;
    private final AbsenRepository absenRepository;
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;


    // fungsi untuk narik data project mana aja yang dipegang si leader
    public ResponseEntity<Map<String, Object>> leaderProjectDetails(String projectId, String tokenWithBearer){
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                // Retrieve project IDs associated with the 'nik'
                List<ProjectDetails> projectIds = retrieveProjectDetailsForNik(nik);
    
                if (!projectIds.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Project IDs retrieved successfully");
                    response.put("data", projectIds);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No project IDs found for the 'nik'");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve project IDs");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private List<ProjectDetails> retrieveProjectDetailsForNik(String nik) {
    try {
        List<PenempatanEntity> penempatanEntities = penempatanRepository.findAllByNik(nik);

        List<ProjectDetails> projectDetails = new ArrayList<>();

        for (PenempatanEntity penempatanEntity : penempatanEntities) {
            String projectId = penempatanEntity.getProjectId().toString();
            ProjectEntity project = projectRepository.findByProjectId(projectId);

            if (project != null) {
                ProjectDetails details = new ProjectDetails();
                details.setProjectId(project.getProjectId());
                details.setProjectName(project.getNamaProject());
                projectDetails.add(details);
            }
        }

        return projectDetails;
    } catch (Exception e) {
        return new ArrayList<>();
    }
}








    // fungsi untuk narik data absen secara keseluruhan dengan projectId yang udah dipilih dari front-end
    public ResponseEntity<Map<String, Object>> getAbsenFromProjectId(ProjectEntity projectId, String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                List<AbsenEntity> absenEntities = absenRepository.findAllByProjectId(projectId);

                if (!absenEntities.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Absen data retrieved successfully");
                    response.put("data", absenEntities);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Absen data found for the provided projectId");

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token is not valid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Absen data");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
