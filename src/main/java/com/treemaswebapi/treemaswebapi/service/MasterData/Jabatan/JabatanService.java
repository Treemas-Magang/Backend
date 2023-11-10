package com.treemaswebapi.treemaswebapi.service.MasterData.Jabatan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Jabatan.request.JabatanRequest;
import com.treemaswebapi.treemaswebapi.entity.JabatanEntity.JabatanEntity;
import com.treemaswebapi.treemaswebapi.repository.JabatanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JabatanService {
    private final JabatanRepository jabatanRepository;

    public ResponseEntity<Map<String, Object>> jabatanAdd(
        JabatanRequest request
    ) {
        try {
        var jabatanEntity = JabatanEntity.builder()
            .jabatanId(request.getJabatanId())
            .namaJabatan(request.getNamaJabatan())
        .build();
        jabatanRepository.save(jabatanEntity);
        Map<String, Object> data = new HashMap<>();
        data.put("user", jabatanEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Jabatan Created");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Create Jabatan");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> jabatanGet() {
        try {
            List<JabatanEntity> jabatan = jabatanRepository.findAll();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", jabatan);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve jabatan");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
