package com.treemaswebapi.treemaswebapi.service.DownloadApkService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.postgresql.util.PGobject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.UploadApkEntity.UploadApkEntity;
import com.treemaswebapi.treemaswebapi.repository.UploadApkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DownloadApkService {

    private final UploadApkRepository uploadApkRepository;

    public ResponseEntity<Resource> downloadApk(String filename) {
        Optional<UploadApkEntity> uploadApkOptional = uploadApkRepository.findByFileName(filename);

        if (uploadApkOptional.isPresent()) {
            UploadApkEntity uploadApk = uploadApkOptional.get();
            byte[] apkData = convertPGObjectToByteArray(uploadApk.getApkData());
            ByteArrayResource resource = new ByteArrayResource(apkData);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + uploadApk.getFileName())
                    .contentLength(apkData.length)
                    .body(resource);
        } else {
            return handleFileNotFound();
        }
    }

    private ResponseEntity<Resource> handleFileNotFound() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Failed");
        response.put("message", "File not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ByteArrayResource(new byte[0]));
    }

    private ResponseEntity<Resource> handleFileDownloadFailure(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Failed");
        response.put("message", "Failed to download file");
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ByteArrayResource(new byte[0]));
    }

    private byte[] convertPGObjectToByteArray(PGobject pgObject) {
        try {
            return pgObject.getValue().getBytes();
        } catch (Exception e) {
            // Handle the exception or log it accordingly
            return new byte[0];
        }
    }

    public ResponseEntity<Map<String, Object>> getVersion() {
        try {
            Optional<UploadApkEntity> latestApk = uploadApkRepository.findTopByOrderByDtmCrtDesc();
            
            if (latestApk.isPresent()) {
                String version = extractAfterUnderscore(latestApk.get().getFileName());
    
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "Version retrieved successfully");
                response.put("data", "versi terbaru adalah "+ version);
    
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Not Success");
                response.put("message", "Version CANNOT BE retrieved");
    
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Not Success");
            response.put("message", e.getMessage());
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private String extractAfterUnderscore(String input) {
        int underscoreIndex = input.indexOf('_');
    
        if (underscoreIndex != -1 && underscoreIndex < input.length() - 1) {
            // If underscore is found and it's not the last character
            return input.substring(underscoreIndex + 1);
        } else {
            // Return an empty string or handle the case where underscore is not found
            return "";
        }
    }
}
