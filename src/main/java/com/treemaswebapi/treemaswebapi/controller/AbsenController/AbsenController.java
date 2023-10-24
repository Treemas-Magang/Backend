package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
import com.treemaswebapi.treemaswebapi.service.AbsenImageService.AbsenImageService;
import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/absen")
@RequiredArgsConstructor
public class AbsenController {
private final AbsenService project;
private final AbsenImageService imageService;

    @GetMapping("/project-list")
    public ResponseEntity<Map<String, Object>> projectList(@RequestBody ProjectListRequest request, @RequestHeader("Authorization") String jwtToken) {
        ResponseEntity<Map<String, Object>> response = project.projectList(request, jwtToken);
        return response;    
    }
    
    @PostMapping("/absen-proof")
    public ResponseEntity<Map<String,String>> uploadAbsenProof(@RequestParam("file") MultipartFile file) throws IOException{
        ResponseEntity<Map<String,String>> response = imageService.uploadImage(file);
        return response; 
    }


}
