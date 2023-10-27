package com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestPart("foto") MultipartFile foto,
        @RequestPart("fotoKtp") MultipartFile fotoKtp,
        @RequestPart("fotoNpwp") MultipartFile fotoNpwp,
        @RequestPart("fotoKk") MultipartFile fotoKk,
        @RequestPart("fotoAsuransi") MultipartFile fotoAsuransi,
        @RequestPart("request") KaryawanAddRequest request
    ) {
        ResponseEntity<Map<String, String>> response = service.karyawanAdd(request, foto, fotoKtp, fotoNpwp, fotoKk, fotoAsuransi);
        return response;
    }
}
