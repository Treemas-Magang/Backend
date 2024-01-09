package com.treemaswebapi.treemaswebapi.service.Upload.Apk;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import org.postgresql.util.PGobject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Announcement.request.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.entity.AnnouncementEntity.AnnouncementEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.UploadApkEntity.UploadApkEntity;
import com.treemaswebapi.treemaswebapi.repository.UploadApkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadApkService {
    private final UploadApkRepository uploadApkRepository;

    public ResponseEntity<Map<String, Object>> uploadApk(MultipartFile file) {
        try {
            UploadApkEntity existingApk = uploadApkRepository.findById(1L).orElse(null);

            if (existingApk != null) {
                // Lakukan operasi penyimpanan file atau manipulasi lain sesuai kebutuhan

                // Ganti data lama dengan data baru
                existingApk.setFileName(file.getOriginalFilename());

                PGobject pgObject = new PGobject();
                pgObject.setType("bytea");
                pgObject.setValue(new String(file.getBytes()));
                existingApk.setApkData(pgObject);

                existingApk.setDtmCrt(new Timestamp(System.currentTimeMillis())); // Update timestamp

                uploadApkRepository.save(existingApk);

                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "File replaced successfully");
                response.put("data", file.getOriginalFilename());

                return ResponseEntity.ok(response);
            } else {
                // Jika tidak ada data terakhir, tambahkan data baru
                UploadApkEntity newUploadApk = new UploadApkEntity();
                newUploadApk.setFileName(file.getOriginalFilename());

                PGobject pgObject = new PGobject();
                pgObject.setType("bytea");
                pgObject.setValue(new String(file.getBytes()));
                newUploadApk.setApkData(pgObject);

                newUploadApk.setDtmCrt(new Timestamp(System.currentTimeMillis())); // Set timestamp baru

                uploadApkRepository.save(newUploadApk);

                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "File uploaded successfully");
                response.put("data", file.getOriginalFilename());

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            // Tangkap exception jika terjadi kesalahan saat menyimpan file
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to upload or replace file");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

