package com.treemaswebapi.treemaswebapi.controller.ProfieController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.ProfieController.request.ProfileRequest;
import com.treemaswebapi.treemaswebapi.service.ProfileService.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;
    
    @PutMapping("/update-profile")
    public ResponseEntity<Map<String, String>> updateProfile(
        @RequestPart("request") ProfileRequest request,
        @RequestHeader("Authorization") String jwtToken,
        @RequestPart(value = "foto", required = false) MultipartFile foto,
        @RequestPart(value = "fotoKtp", required = false) MultipartFile fotoKtp,
        @RequestPart(value = "fotoNpwp", required = false) MultipartFile fotoNpwp,
        @RequestPart(value = "fotoKk", required = false) MultipartFile fotoKk,
        @RequestPart(value = "fotoAsuransi", required = false) MultipartFile fotoAsuransi
    ) {
        ResponseEntity<Map<String, String>> response = service.updateProfile(request, jwtToken, foto, fotoKtp, fotoNpwp, fotoKk, fotoAsuransi);
        return response;    
    }
}
