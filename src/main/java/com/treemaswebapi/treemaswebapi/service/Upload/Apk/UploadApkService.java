package com.treemaswebapi.treemaswebapi.service.Upload.Apk;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Announcement.request.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.entity.AnnouncementEntity.AnnouncementEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.UploadApkEntity.UploadApkEntity;
import com.treemaswebapi.treemaswebapi.repository.UploadApkRepository;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadApkService {
    private final UploadApkRepository uploadApkRepository;
    public ResponseEntity<Map<String, Object>> uploadApk(@RequestParam("file") MultipartFile file)  {
        try {
            // Lakukan operasi penyimpanan file atau manipulasi lain sesuai kebutuhan
            // Simpan informasi file ke database
            UploadApkEntity uploadApk = new UploadApkEntity();
            uploadApk.setFileName(file.getOriginalFilename());
            uploadApk.setApkData(file.getBytes()); // Set data APK sebagai byte array

            uploadApkRepository.save(uploadApk);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "File uploaded successfully");
            response.put("data", file.getOriginalFilename());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Tangkap exception jika terjadi kesalahan saat menyimpan file
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to upload file");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
