package com.treemaswebapi.treemaswebapi.service.UpdateListProjectService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectDetails;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateListProjectService {
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;
    private final PenempatanRepository penempatanRepository;
    private final KaryawanRepository karyawanRepository;
    private final PenempatanEntity penempatanEntity;
public ResponseEntity<Map<String, Object>> listProject(@RequestHeader String tokenWithBearer, @RequestBody String projectId){
    Map<String, Object> response = new HashMap<>();
    try{
        if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                List<ProjectEntity> allProjects = projectRepository.findAll();
                Optional<PenempatanEntity> penempatanOptional = penempatanRepository.findByNikAndActive(nik,"1");

                if (penempatanOptional.isPresent()){
                    ProjectEntity activeProjectId = penempatanOptional.get().getProjectId();
                    response.put("success", true);
                    response.put("message", "Projects retrieved successfully");
                    response.put("activeProjectId", activeProjectId);
                    response.put("success", allProjects);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
                    }else{
                    response.put("success", true);
                    response.put("message", "Projects retrieved successfully");
                    response.put("activeProjectId", "active project belom ada");
                    response.put("projects", allProjects);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
                    }
                }else{
                    response.put("success", false);
                    response.put("message", "Invalid token format");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
    } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update list of projects");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
public ResponseEntity<Map<String, Object>> updateProject(@RequestHeader String tokenWithBearer, @RequestBody List<ProjectEntity> selectedProjects){
    try{
        if(tokenWithBearer.startsWith("Bearer ")){
            String token = tokenWithBearer.substring("Bearer ".length());
            String nik = jwtService.extractUsername(token);
            String nama = karyawanRepository.findNamaByNik(nik);

            for (ProjectEntity projectId : selectedProjects){
                Optional<ProjectEntity> projectOptional = projectRepository.findByProjectIds(projectId);
                if (projectOptional.isPresent()){
                    // masukin ke penempatanEntity
                    penempatanEntity.setNik(nik);
                    penempatanEntity.setActive("1");
                    penempatanEntity.setProjectId(projectId);
                    penempatanEntity.setUsrUpd(nama);
                    penempatanEntity.setDtmUpd(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

                    //save ke penempatanEntity
                    penempatanRepository.save(penempatanEntity);

                    // kasih response
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil update project");
                    response.put("data", penempatanEntity);
                }
            }
        }      
    } catch (Exception e) {
        Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update list of projects");
            response.put("error", e.getMessage());
    }
    return null;
}
}