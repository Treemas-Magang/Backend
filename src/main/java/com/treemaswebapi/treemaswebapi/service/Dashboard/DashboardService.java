package com.treemaswebapi.treemaswebapi.service.Dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.Dashboard.DashboardResponse;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanImageEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanImageRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AbsenRepository absenRepository;
    private final JwtService jwtService;
    private final KaryawanRepository karyawanRepository;
    private final KaryawanImageRepository KaryawanImageRepository;

    public ResponseEntity<Map<String, Object>> dashboardGet(@RequestHeader("Authorization") String jwtToken, DashboardResponse dashboardResponse) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String nik = jwtService.extractUsername(token);

            Optional<KaryawanEntity> karyawan = karyawanRepository.findByNik(nik);
            String nama = karyawan.get().getNama();
            int masuk = absenRepository.countByJamMskIsNotNullAndNik(nik);// perubahan ini gue lakiun tanggal 13.12.2023 -Aliy
            int totalMasuk = masuk;
            int totalSakit = absenRepository.countByIsSakitAndNik("1", nik);
            int totalTelatMasuk = absenRepository.countByNoteTelatMskIsNotNullAndNik(nik);
            int totalCuti = absenRepository.countByIsCutiAndNik("1", nik);
            int totalTidakMasuk = absenRepository.countByJamMskIsNullAndJamPlgIsNullAndNik(nik);
            dashboardResponse.setNama(nama);
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

    public ResponseEntity<Map<String, Object>> dashboardGetFoto(@RequestHeader("Authorization") String jwtToken) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String nik = jwtService.extractUsername(token);

            Optional<KaryawanImageEntity> karyawan = KaryawanImageRepository.findByNik(nik);
            String karyawanImg = karyawan.get().getFoto();
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", karyawanImg);
            
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
