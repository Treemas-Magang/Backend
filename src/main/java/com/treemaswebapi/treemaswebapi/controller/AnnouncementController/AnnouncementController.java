package com.treemaswebapi.treemaswebapi.controller.AnnouncementController;


import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> createAnnouncement(@RequestBody AnnouncementRequest request, @RequestHeader("Authorization") String token) {
        ResponseEntity<Map<String, Object>> response = service.createAnnouncement(request, token);
        return response;
    }

    @GetMapping("/get/{idAnn}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable int request, @RequestHeader("Authorization") String token) {
        ResponseEntity<Announcement> response = service.getAnnouncementById(request);
        return response;
    }

    @PutMapping("/update/{idAnn}")
    public ResponseEntity<Announcement> updateAnnouncement(
        @PathVariable int idAnn,
        @RequestBody AnnouncementRequest newAnnouncement
    ) {
        ResponseEntity<Announcement> response = service.updateAnnouncement(idAnn, newAnnouncement);
        return response;
    }

    @DeleteMapping("/delete/{idAnn}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable int idAnn) {
        ResponseEntity<Void> response = service.deleteAnnouncement(idAnn);
        return response;
    }
}
