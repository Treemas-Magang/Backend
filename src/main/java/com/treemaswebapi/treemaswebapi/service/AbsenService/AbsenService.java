package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenImgEntity;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectDetails;
import com.treemaswebapi.treemaswebapi.repository.AbsenImgRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenService {

    private final JwtService jwtService;
    private final PenempatanRepository penempatanRepository;
    private final ProjectRepository projectRepository;
    private final AbsenRepository absenRepository;
    private final AbsenImgRepository absenImgRepository;

    public ResponseEntity<?> getProjectDetails(String tokenWithBearer) {
        List<ProjectDetails> projectDetails = new ArrayList<>(); // Declare projectDetails outside the if block

        try {
            // Check if the token is valid, and extract 'nik' (assuming it's the user ID) from the token.
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                // Look for the projectId(s) that the user has based on their 'nik'.
                List<String> projectIds = penempatanRepository.findProjectIdByNik(nik);

                // Iterate through each projectId and fetch project details.
                for (String projectId : projectIds) {
                    ProjectEntity project = projectRepository.findByProjectId(projectId);
                    if (project != null){
                        ProjectDetails projectDetail = new ProjectDetails();
                        projectDetail.setProjectName(project.getNamaProject());
                        projectDetail.setProjectAddress(project.getLokasi());
                        projectDetails.add(projectDetail);
                    }
                }
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
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
    public ResponseEntity<?> updateProject(String tokenWithBearer, @RequestBody String projectId) {
        try {
            String nik = null; // Initialize nik
    
            // Check if the token is valid, and extract 'nik' (assuming it's the user ID) from the token.
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                nik = jwtService.extractUsername(token);
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
            }
    
            if (nik != null) {
                // Create a new PenempatanEntity
                PenempatanEntity penempatanEntity = new PenempatanEntity();
                penempatanEntity.setNik(nik);
                penempatanEntity.setProjectId(projectId);
                // Set other fields as needed, such as id, dtmupd, and active.
    
                // You can obtain the current timestamp for dtmupd, for example:
                penempatanEntity.setDtmUpd(null);
    
                // Set the active status
                penempatanEntity.setActive("1");
    
                // Save the entity to the database using the repository
                penempatanEntity = penempatanRepository.save(penempatanEntity);
    
                // Create a response object with a success message
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Project updated successfully");
                response.put("data", penempatanEntity);
    
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                // Handle the case where nik is null (e.g., token validation failed)
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Unable to update project");
    
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the process
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update project");
            response.put("error", e.getMessage());
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    public ResponseEntity<?> inputAbsen(String tokenWithBearer, @RequestBody Map<String, Object> requestBody, @RequestParam("photoAbsen") MultipartFile photoAbsen) {
    try {
        // 1. Extract the user's nik from the provided token.
        String nik = null;

        if (tokenWithBearer.startsWith("Bearer ")) {
            String token = tokenWithBearer.substring("Bearer ".length());
            nik = jwtService.extractUsername(token);
        } else {
            // Handle the case where "Bearer " is not present
            System.out.println("Invalid token format");
        }

        if (nik != null) {
            // 2. Retrieve the projectId from penempatanEntity based on the user's nik.
            String projectId = requestBody.get("projectId").toString();

            List<String> registeredProjects = penempatanRepository.findProjectIdByNik(nik);
            if (registeredProjects.contains(projectId)) {
                // 3. Check if the user's projectId exists in the list of registered projects from penempatanEntity.
                    AbsenEntity absenEntity = new AbsenEntity();
                    absenEntity.setProjectId(projectId);
                    absenEntity.setNik(nik);
                    absenEntity.setNama(requestBody.get("namaProject").toString());
                    absenEntity.setLokasiMsk(requestBody.get("lokasi").toString());
                    absenEntity.setJamMsk(null);
                    absenEntity.setJarakMsk(requestBody.get("jarak").toString());
                    absenEntity.setTglAbsen(LocalDate.now());
                    absenEntity.setNoteTelatMsk(requestBody.get("noteTelatMsk").toString());

                    // Save AbsenEntity to the database
                    absenEntity = absenRepository.save(absenEntity);

                    AbsenImgEntity absenImgEntity = new AbsenImgEntity();
                    absenImgEntity.setNik(nik);
                    absenImgEntity.setImage64(photoAbsen.toString());

                    // Save AbsenImgEntity to the database
                    absenImgEntity = absenImgRepository.save(absenImgEntity);

                    // Create a response object with a success message
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Absen data inserted successfully");
                    response.put("data", absenEntity);

                    return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                // Handle the case where projectId is not found
                // Return an error response
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Project not found");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } else {
            // Handle the case where nik is null (e.g., token validation failed)
            // Return an error response
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Unable to insert absen data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    } catch (Exception e) {
        // Handle any exceptions that may occur during the process
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Failed to insert absen data");
        response.put("error", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
}
