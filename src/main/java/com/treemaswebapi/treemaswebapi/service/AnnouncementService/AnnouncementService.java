package com.treemaswebapi.treemaswebapi.service.AnnouncementService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AnnouncementController.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.entity.Announcement;
import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.AnnouncementRepo;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final UserRepository userRepository;
    private final AnnouncementRepo announcementRepo;
    private final JwtService jwtService;

    public ResponseEntity<Map<String, Object>> createAnnouncement(AnnouncementRequest request, @RequestHeader("Authorization") String token) {
        try {
            String dapetToken = token.replace("Bearer ","");

            String nik = jwtService.extractUsername(dapetToken);

            Optional<UserEntity> userOptional = userRepository.findByNik(nik);

            long jmlAnnouncement = announcementRepo.count();
            
            // kalau ketemu di database
            if(userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                String namaKaryawan = user.getNamaKaryawan();

                Announcement newAnnouncement = Announcement.builder()
                .idAnn(1)
                .titleAnn(request.getTitleAnn())
                .bodyAnn(request.getBodyAnn())
                .createdBy(namaKaryawan)
                .dateAnn(new Date())
                .build();
                announcementRepo.save(newAnnouncement);
                
                Map<String, Object> response = new HashMap<>();
                response.put("data: ", newAnnouncement);
                response.put("jumlah announcement: ", jmlAnnouncement);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }   else {
                Map<String, Object> response = new HashMap<>();
                response.put("NIK anda tidak ditemukan.", nik);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }  
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<Announcement> getAnnouncementById(int idAnn) {
        Announcement announcement = announcementRepo.findByIdAnn(idAnn).orElse(null);
        if (announcement != null) {
            return ResponseEntity.status(HttpStatus.OK).body(announcement);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Announcement> updateAnnouncement(int idAnn, AnnouncementRequest request) {
        Announcement existingAnnouncement = announcementRepo.findById(idAnn).orElse(null);
        if (existingAnnouncement != null) {
            // Update the existing announcement with the new data
            existingAnnouncement.setTitleAnn(request.getTitleAnn());
            existingAnnouncement.setBodyAnn(request.getBodyAnn());
            existingAnnouncement.setCreatedBy(request.getCreatedBy());

            Announcement updatedAnnouncement = announcementRepo.save(existingAnnouncement);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAnnouncement);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> deleteAnnouncement(int idAnn) {
        if (announcementRepo.existsById(idAnn)) {
            announcementRepo.deleteById(idAnn);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
