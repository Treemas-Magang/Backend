package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.AbsenRequest;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.ProjectListRequest;
import com.treemaswebapi.treemaswebapi.entity.AbsenMaster;
import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
import com.treemaswebapi.treemaswebapi.repository.ProjectMasterRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.treemaswebapi.treemaswebapi.repository.ListMemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenService {

    private final ProjectMasterRepo projectRepo;
    private final ListMemberRepo listMemberProjectRepo;
    private final JwtService jwtService;

    public ResponseEntity <Map<String, Object>> projectList (ProjectListRequest projectListRequest, @RequestHeader("Authorization") String jwtToken) {
    try {
         // Assuming the jwtToken is in the Authorization header
        String token = jwtToken.replace("Bearer ", "");
        System.out.println("TOKENNNNN : "+token);
        if (token != null) {
            // Remove the "Bearer " prefix if it exists
            String userNik = jwtService.extractUsername(token);
            System.out.println(userNik);
            // Step 2: Query listmember_project to get the matching kode_project values
            String matchingProjectCodes = listMemberProjectRepo.findKodeProjectByNik(userNik);

            // Step 3: Query projectmaster to fetch project details for matching kode_project values
            String projectDetails = projectRepo.findAllByKodeProject(matchingProjectCodes);

            Map<String, Object> responseOk = new HashMap<>();
            responseOk.put("status: ", "sudah absen");
            responseOk.put("data: ", projectDetails);
            responseOk.put("status token: ", "valid");
            return ResponseEntity.status(HttpStatus.OK).body(responseOk);
        } else {
            Map<String, Object> responseFailed = new HashMap<>();
            responseFailed.put("status: ", "absen terlebih dahulu");
            responseFailed.put("data: ", null);
            responseFailed.put("status token: ", "tidak valid");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseFailed); // Unauthorized if no or invalid token
        }
    } catch (Exception e) {
            Map<String, Object> responseFailed = new HashMap<>();
            responseFailed.put("status: ", "absen terlebih dahulu");
            responseFailed.put("data: ", null);
            responseFailed.put("status token: ", "tidak valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}

    ResponseEntity <Map<String, Object>> createMasuk (AbsenRequest request) {
    try{
    //masih kurang kondisi kalo dia wfh atau wfo, pokonya kalo dia wfh dia requestnya narik image
    String kodeProject = request.getKodeProject();
    ProjectMaster project = projectRepo.findAlamatProjectByKodeProject(kodeProject);
    var masuk = AbsenMaster.builder()
        .kodeProject(project)
        .jamMasuk(request.getWaktu())
        .lokasiMasuk(request.getLokasiSekarang())
        .jarak(request.getJarak())
        .catatanTerlambat(request.getCatatanTerlambat())
        .build();
        Map<String, Object> response = new HashMap<>();
        response.put("status: ", "success");
        response.put("data: ", masuk);
        response.put("message: ", "data berhasil diinput!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
        Map<String, Object> response = new HashMap<>();
        response.put("status","failed");
        response.put("data",null);
        response.put("message", "data gagal diinput.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        }
    }
}
