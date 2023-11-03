package com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan.request.KaryawanAddRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan.KaryawanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-data")
public class KaryawanController {
    
    private final KaryawanService service;

    @PostMapping("/karyawan-form/add")
    public ResponseEntity<Map<String, String>> karyawanAdd(
        @RequestPart(value = "foto", required = false) MultipartFile foto,
        @RequestPart(value = "fotoKtp", required = false) MultipartFile fotoKtp,
        @RequestPart(value = "fotoNpwp", required = false) MultipartFile fotoNpwp,
        @RequestPart(value = "fotoKk", required = false) MultipartFile fotoKk,
        @RequestPart(value = "fotoAsuransi", required = false) MultipartFile fotoAsuransi,
        @RequestPart("request") KaryawanAddRequest request,
        @RequestHeader("Authorization") String jwtToken
    ) {
        ResponseEntity<Map<String, String>> response = service.karyawanAdd(request, foto, fotoKtp, fotoNpwp, fotoKk, fotoAsuransi, jwtToken);
        return response;
    }
}
