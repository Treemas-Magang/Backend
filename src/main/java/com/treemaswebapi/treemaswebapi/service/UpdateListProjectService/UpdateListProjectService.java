package com.treemaswebapi.treemaswebapi.service.UpdateListProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
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
public Map<String, Object> updateListProject(@RequestHeader String tokenWithBearer, @RequestBody String projectId){
    Map<String, Object> response = new HashMap<>();
    try{
        if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                
                List<ProjectEntity> allProjects = projectRepository.findAll();
                Optional<PenempatanEntity> penempataOptional = penempatanRepository.findByNikAndActive(nik,"1");

                if (penempataOptional.isPresent()){
                    ProjectEntity activeProjectId = penempataOptional.get().getProjectId();
                    response.put("success", true);
                    response.put("message", "Projects retrieved successfully");
                    response.put("activeProjectId", activeProjectId);
                    response.put("success", allProjects);
                    }else{
                    response.put("success", true);
                    response.put("message", "Projects retrieved successfully");
                    response.put("projects", allProjects);
                    }
                }else{
                    response.put("success", false);
                    response.put("message", "Invalid token format");
                }
    } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update list of projects");
            response.put("error", e.getMessage());
    }
    return response;
}
}