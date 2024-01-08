package com.treemaswebapi.treemaswebapi.controller.DownloadApk;

import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.DownloadApkService.DownloadApkService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/download")
public class DownloadApkController {
    private final DownloadApkService service;

    @GetMapping("/apk")
    public ResponseEntity<Resource> downloadApk(@RequestParam("filename") String filename) {
        ResponseEntity<Resource> response = service.downloadApk(filename);
        return response;
    }
}