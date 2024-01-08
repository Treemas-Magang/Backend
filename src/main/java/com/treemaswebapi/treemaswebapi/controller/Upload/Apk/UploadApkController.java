package com.treemaswebapi.treemaswebapi.controller.Upload.Apk;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Announcement.request.AnnouncementRequest;
import com.treemaswebapi.treemaswebapi.service.Upload.Apk.UploadApkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadApkController {
    private final UploadApkService service;

    @PostMapping("/apk")
    public ResponseEntity<Map<String, Object>> uploadApk(
        @RequestParam("file") MultipartFile file
    ) {
        ResponseEntity<Map<String, Object>> response = service.uploadApk(file);
        return response;
    }
}
