package com.treemaswebapi.treemaswebapi.service.MasterData.Cuti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.MasterData.Cuti.request.MasterCutiRequest;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.MasterCutiEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.MasterCutiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CutiService {
    
    private final JwtService jwtService;
    private final MasterCutiRepository masterCutiRepository;
    private final KaryawanRepository karyawanRepository;
    
    public ResponseEntity<Map<String, Object>> cutiAdd(
        MasterCutiRequest request,
        @RequestHeader("Authorization") String jwtToken
    ) {
        try {
              // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();

            String id = request.getId();
            Optional<MasterCutiEntity> existingId = masterCutiRepository.findById(id);

            if(existingId.isPresent()) {
                int nextNumber = 1;
                String newId;
                do {
                    nextNumber++;
                    newId = id + nextNumber;
                } while (masterCutiRepository.existsById(newId));
                id = newId;
                System.out.println("Masuk Id Generate");
            } else {
                id = request.getId();
                System.out.println("Masuk Id Baru Pertama Kali");
            }

            System.out.println(nama);
            var masterCutiEntity = MasterCutiEntity.builder()
                .id(id)
                .cutiDesc(request.getCutiDesc())
                .value(request.getValue())
                .usrCrt(nama)
            .build();
            masterCutiRepository.save(masterCutiEntity);
                
            Map<String, Object> data = new HashMap<>();
            data.put("user", masterCutiEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Cuti Created");
            response.put("data", data);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Create Cuti");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    public ResponseEntity<Map<String, Object>> cutiGet() {
        try {
            List<MasterCutiEntity> masterCuti = masterCutiRepository.findAll();
            Map<String, Object> data = new HashMap<>();
            data.put("user", masterCuti);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve tipe claims");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
