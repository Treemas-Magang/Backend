package com.treemaswebapi.treemaswebapi.service.MasterData.Announcement;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.MasterData.Announcement.request.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.entity.AnnouncementEntity.AnnouncementEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.repository.AnnouncementRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
   
    private final AnnouncementRepository announcementRepository;
    private final KaryawanRepository karyawanRepository;
    private final JwtService jwtService;

    public ResponseEntity<Map<String, Object>> announcementAdd(
        @RequestPart(value = "image", required = false) MultipartFile image,
        AnnouncementRequest request,
        @RequestHeader("Authorization") String jwtToken
    )  {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();

            System.out.println("INI TOKEN : "+token);
            System.out.println("INI USER DARI TOKEN : "+userToken);

            // Kirim ke tbl_announcement
            var announcement = AnnouncementEntity.builder()
                .title(request.getTitle())
                .header(request.getHeader())
                .note(request.getNote())
                .image(image != null ? convertToBase64(image) : null)
                .image64(image.getOriginalFilename())
                .footer(request.getFooter())
                .usrCrt(nama)
            .build();
            announcementRepository.save(announcement);
            Map<String, Object> data = new HashMap<>();
            data.put("user", announcement);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Announcement Created");
            response.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed!");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Helper method to convert MultipartFile to Base64
    private String convertToBase64(MultipartFile file) {
        try {
            if (file != null) {
                byte[] bytes = file.getBytes();
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (IOException e) {
            // Handle the exception, for example, log it
            e.printStackTrace();
        }
        return null; // or an empty string if needed
    }
}
