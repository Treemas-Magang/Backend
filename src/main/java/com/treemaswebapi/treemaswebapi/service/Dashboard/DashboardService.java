package com.treemaswebapi.treemaswebapi.service.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.Dashboard.DashboardResponse;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AbsenRepository absenRepository;
    private final JwtService jwtService;

    public ResponseEntity<Map<String, Object>> dashboardGet(@RequestHeader("Authorization") String jwtToken, DashboardResponse dashboardResponse) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String nik = jwtService.extractUsername(token);

            int totalMasuk = absenRepository.countByIsAbsenAndNik("1", nik);
            int totalSakit = absenRepository.countByIsSakitAndNik("1", nik);
            int totalTelatMasuk = absenRepository.countByNoteTelatMskIsNotNullAndNik(nik);
            int totalCuti = absenRepository.countByIsCutiAndNik("1", nik);
            int totalTidakMasuk = absenRepository.countByJamMskIsNullAndJamPlgIsNullAndNik(nik);
            dashboardResponse.setTotalMasuk(totalMasuk);
            dashboardResponse.setTotalSakit(totalSakit);
            dashboardResponse.setTotalTelatMasuk(totalTelatMasuk);
            dashboardResponse.setTotalCuti(totalCuti);
            dashboardResponse.setTotalTidakMasuk(totalTidakMasuk);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", dashboardResponse);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve absen");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // public ResponseEntity<Map<String, Object>> dashboardGet(DashboardResponse dashboardResponse) {
    //     try {
    //         // Ambil semua member dari tabel absen
    //         List<String> allMembers = absenRepository.findAllMembers(); // Gantilah dengan metode yang sesuai

    //         // Buat daftar untuk menyimpan hasil perhitungan
    //         List<DashboardMemberResponse> memberResponses = new ArrayList<>();

    //         int totalMasuk = absenRepository.countByIsAbsenAndNik("1", nik);
    //         int totalSakit = absenRepository.countByIsSakitAndNik("1", nik);
    //         int totalTelatMasuk = absenRepository.countByNoteTelatMskIsNotNullAndNik(nik);
    //         int totalCuti = absenRepository.countByIsCutiAndNik("1", nik);
    //         int totalTidakMasuk = absenRepository.countByJamMskIsNullAndJamPlgIsNullAndNik(nik);
    //         dashboardResponse.setTotalMasuk(totalMasuk);
    //         dashboardResponse.setTotalSakit(totalSakit);
    //         dashboardResponse.setTotalTelatMasuk(totalTelatMasuk);
    //         dashboardResponse.setTotalCuti(totalCuti);
    //         dashboardResponse.setTotalTidakMasuk(totalTidakMasuk);

    //         Map<String, Object> response = new HashMap<>();
    //         response.put("status", "Success");
    //         response.put("message", "Retrieved");
    //         response.put("data", dashboardResponse);
            
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (Exception e) {
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("status", "Failed");
    //         response.put("message", "Failed to retrieve absen");
    //         response.put("error", e.getMessage());
    //         System.out.println(e);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //     }
    // }
}
