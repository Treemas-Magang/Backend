package com.treemaswebapi.treemaswebapi.service.AnnouncementService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.treemaswebapi.treemaswebapi.controller.AnnouncementController.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.entity.Announcement;
import com.treemaswebapi.treemaswebapi.repository.AnnouncementRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepo announcementRepo;

    public ResponseEntity<Announcement> createAnnouncement(AnnouncementRequest request) {
        try {
            Announcement newAnnouncement = Announcement.builder()
                .titleAnn(request.getTitleAnn())
                .bodyAnn(request.getBodyAnn())
                .createdBy(request.getCreatedBy())
                .build();

            Announcement createdAnnouncement = announcementRepo.save(newAnnouncement);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<Announcement> getAnnouncementById(String idAnn) {
        Announcement announcement = announcementRepo.findById(idAnn).orElse(null);
        if (announcement != null) {
            return ResponseEntity.status(HttpStatus.OK).body(announcement);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Announcement> updateAnnouncement(String idAnn, AnnouncementRequest request) {
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

    public ResponseEntity<Void> deleteAnnouncement(String idAnn) {
        if (announcementRepo.existsById(idAnn)) {
            announcementRepo.deleteById(idAnn);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
