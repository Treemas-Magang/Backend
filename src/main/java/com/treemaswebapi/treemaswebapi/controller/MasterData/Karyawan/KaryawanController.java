package com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan.request.KaryawanAddRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan.KaryawanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-data")
public class KaryawanController {
    
    private final KaryawanService service;

    @PostMapping("/karyawan-form/add")
    public ResponseEntity<Map<String, String>> karyawanAdd(@RequestBody KaryawanAddRequest request) {
        ResponseEntity<Map<String, String>> response = service.karyawanAdd(request);
        return response;
    }
}
