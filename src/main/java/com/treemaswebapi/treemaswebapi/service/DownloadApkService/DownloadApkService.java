package com.treemaswebapi.treemaswebapi.service.DownloadApkService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            ByteArrayResource resource = new ByteArrayResource(uploadApk.getApkData());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + uploadApk.getFileName())
                    .contentLength(uploadApk.getApkData().length)
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
}
