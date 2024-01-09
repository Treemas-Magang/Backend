package com.treemaswebapi.treemaswebapi.controller.Upload.Absen;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.service.Upload.Absen.UploadAbsenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadAbsenController {
    private final UploadAbsenService service;

    @GetMapping("/get-absen")
    public ResponseEntity<Map<String, Object>> getAbsenSendiriWeb(
        @RequestHeader("Authorization") String jwtToken
    ) {
        ResponseEntity<Map<String, Object>> response = service.getAbsenSendiriWeb(jwtToken);
        return response;
    }

    @PostMapping("/add-absen")
    public ResponseEntity<Map<String, Object>> inputAbsenWeb(
        @RequestHeader("Authorization") String jwtToken,
        @RequestBody AbsenRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.inputAbsenWeb(jwtToken, request);
        return response;
    }
}
