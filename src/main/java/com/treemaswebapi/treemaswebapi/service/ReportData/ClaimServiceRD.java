package com.treemaswebapi.treemaswebapi.service.ReportData;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.sql.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.ReportData.request.ClaimRequestRD;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimEntity;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimImageEntity;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.TipeClaimEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.repository.ClaimImageRepository;
import com.treemaswebapi.treemaswebapi.repository.ClaimRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.TipeClaimRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimServiceRD {
    
    private final ClaimRepository claimRepository;
    private final JwtService jwtService;
    private final KaryawanRepository karyawanRepository;
    private final TipeClaimRepository tipeClaimRepository;
    private final ClaimImageRepository claimImageRepository;

    public ResponseEntity<Map<String, Object>> claimGetAll() {
        try {
            
            List<ClaimEntity> claims = claimRepository.findAll(); // Fetch all records from the AnnouncementEntity table

            List<Map<String, Object>> formattedClaims = new ArrayList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (ClaimEntity claim : claims) {
                Map<String, Object> formattedClaim = new HashMap<>();
                formattedClaim.put("id", claim.getId());
            
                
                formattedClaim.put("nik", claim.getNik());
                formattedClaim.put("nama", claim.getNama());
                formattedClaim.put("tanggal", claim.getTanggal());
                formattedClaim.put("nominal", claim.getNominal());
                formattedClaim.put("keterangan", claim.getKeterangan());
                TipeClaimEntity tipeClaimEntity = claim.getTipeClaimEntity();
                if (tipeClaimEntity != null) {
                    formattedClaim.put("tipeClaim", tipeClaimEntity.getKeterangan());
                } else {
                    formattedClaim.put("tipeClaim", "TIPE CLAIM DELETED"); // or any default value you want to set
                }

                formattedClaims.add(formattedClaim);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Claim Retrieved");
            response.put("data", formattedClaims);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve claim");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> claimAddMobile(
        @RequestHeader("Authorization") String jwtToken,
        ClaimRequestRD request
    ) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();

            long currentTimeMillis = System.currentTimeMillis();
            Timestamp dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));

            // Menggunakan LocalDate untuk mendapatkan tanggal hari ini
            LocalDate currentDate = LocalDate.now();

            TipeClaimEntity tipeClaimEntity = tipeClaimRepository.findById(request.getSelectedTipeClaim())
                .orElseThrow(() -> new RuntimeException("TipeClaim not found for id: " + request.getSelectedTipeClaim()));

            // Ke tbl_claim
            var claimAdd = ClaimEntity.builder()
                .nik(userToken)
                .nama(nama)
                .keterangan(request.getKeterangan())
                .nominal(request.getNominal())
                .tanggal(currentDate)
                .dtmCrt(dtmCrt)
                .usrCrt(nama)
                .tipeClaimEntity(tipeClaimEntity)
            .build();

            claimRepository.save(claimAdd);

            // Ke tbl_claim_image
            var claimAddImage = ClaimImageEntity.builder()
                .nik(userToken)
                .tanggal(currentDate)
                .image64(request.getImage64())
                .usrCrt(nama)
                .dtmCrt(dtmCrt)
            .build();

            claimImageRepository.save(claimAddImage);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Claim Added");
            response.put("data", claimAdd);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Add Claim");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
