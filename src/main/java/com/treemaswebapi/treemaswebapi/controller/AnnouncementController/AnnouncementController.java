package com.treemaswebapi.treemaswebapi.controller.AnnouncementController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.treemaswebapi.treemaswebapi.entity.Announcement;
import com.treemaswebapi.treemaswebapi.service.AnnouncementService.AnnouncementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService service;

    @PostMapping("/create")
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody AnnouncementRequest announcement) {
        ResponseEntity<Announcement> response = service.createAnnouncement(announcement);
        return response;
    }

    @GetMapping("/get/{idAnn}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable String idAnn) {
        ResponseEntity<Announcement> response = service.getAnnouncementById(idAnn);
        return response;
    }

    @PutMapping("/update/{idAnn}")
    public ResponseEntity<Announcement> updateAnnouncement(
        @PathVariable String idAnn,
        @RequestBody AnnouncementRequest newAnnouncement
    ) {
        ResponseEntity<Announcement> response = service.updateAnnouncement(idAnn, newAnnouncement);
        return response;
    }

    @DeleteMapping("/delete/{idAnn}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable String idAnn) {
        ResponseEntity<Void> response = service.deleteAnnouncement(idAnn);
        return response;
    }
}
