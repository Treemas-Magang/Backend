package com.treemaswebapi.treemaswebapi.service.MasterData.Announcement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
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
                .image64(image != null ? convertToBase64(image) : null)
                .image(image.getOriginalFilename())
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

    public ResponseEntity<Map<String, Object>> announcementGet(){
        try {
            List<AnnouncementEntity> announcements = announcementRepository.findAll(); // Fetch all records from the AnnouncementEntity table
        
            int totalAnnouncements = announcements.size();

            List<Map<String, Object>> formattedAnnouncements = new ArrayList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (AnnouncementEntity announcement : announcements) {
                Map<String, Object> formattedAnnouncement = new HashMap<>();
                formattedAnnouncement.put("id", announcement.getId());
                
                Date tglUploadDate = new Date(announcement.getTglUpload().getTime());
                String formattedTglUpload = dateFormat.format(tglUploadDate);
                
                formattedAnnouncement.put("tgl_upload", formattedTglUpload);
                formattedAnnouncement.put("title", announcement.getTitle());
                formattedAnnouncement.put("header", announcement.getHeader());
                formattedAnnouncement.put("note", announcement.getNote());
                formattedAnnouncement.put("footer", announcement.getFooter());
                formattedAnnouncement.put("image64", announcement.getImage64());
                
                // Tambahan properti lain yang ingin Anda tampilkan
                formattedAnnouncement.put("usrCrt", announcement.getUsrCrt());

                formattedAnnouncements.add(formattedAnnouncement);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Announcements Retrieved");
            response.put("jumlah_announcement", totalAnnouncements);
            response.put("data", formattedAnnouncements);

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

    public ResponseEntity<Map<String, Object>> announcementUpdate(
        Long id,  
        @RequestPart(value = "image", required = false) MultipartFile image,
        AnnouncementRequest request,
        @RequestHeader("Authorization") String jwtToken
){
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();
            Optional<AnnouncementEntity> announcementOptional = announcementRepository.findById(id);
            if (announcementOptional.isPresent()) {
                AnnouncementEntity announcement = announcementOptional.get();
                // Perbarui properti-properti yang sesuai dengan data baru
                announcement.setTitle(request.getTitle());
                announcement.setHeader(request.getHeader());
                announcement.setNote(request.getNote());
                announcement.setFooter(request.getFooter());
                announcement.setUsrCrt(nama);

            if (image != null) {
                // Jika ada gambar yang diunggah, Anda dapat mengelola gambar di sini
                announcement.setImage64(convertToBase64(image)); // Ubah sesuai dengan cara Anda mengelola gambar
                announcement.setImage(image.getOriginalFilename()); // Ubah sesuai dengan cara Anda mengelola gambar
            }

            announcementRepository.save(announcement);

            Map<String, Object> data = new HashMap<>();
            data.put("user", announcement);

                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "Announcement updated successfully");
                response.put("data", data);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Failed");
                response.put("message", "Announcement not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to update announcement");
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
