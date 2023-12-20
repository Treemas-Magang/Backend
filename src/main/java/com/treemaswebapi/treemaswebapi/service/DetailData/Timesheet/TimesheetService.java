package com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.Dashboard.DashboardResponse;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimesheetService {
    private final AbsenRepository absenRepository;
    private final KaryawanRepository karyawanRepository;
    private final JwtService jwtService;
    
    public ResponseEntity<Map<String, Object>> timesheetGet(@RequestHeader("Authorization") String jwtToken, TimesheetResponse timesheetResponse) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String nik = jwtService.extractUsername(token);

            Optional<KaryawanEntity> karyawan = karyawanRepository.findById(nik);
            String namaKaryawan = karyawan.get().getNama();

            Optional<AbsenEntity> absenUser = absenRepository.findByNik(nik);
            String hari = absenUser.get().getHari();
            LocalDate tglAbsen = absenUser.get().getTglAbsen();
            ProjectEntity projectId = absenUser.get().getProjectId();
            LocalTime jamMsk = absenUser.get().getJamMsk();
            LocalTime jamPlg = absenUser.get().getJamPlg();
            BigDecimal totalJamKerja = absenUser.get().getTotalJamKerja();
            BigDecimal overtime = BigDecimal.ZERO; // Inisialisasi BigDecimal dengan nilai nol

            BigDecimal jamKerja = BigDecimal.valueOf(9);

            if (totalJamKerja.compareTo(jamKerja) > 0) {
                overtime = totalJamKerja.subtract(jamKerja);
            }

            String notePekerjaan = absenUser.get().getNotePekerjaan();

            timesheetResponse.setNik(nik);
            timesheetResponse.setNamaKaryawan(namaKaryawan);
            timesheetResponse.setTglAbsen(tglAbsen);
            timesheetResponse.setProjectId(projectId);
            timesheetResponse.setHari(hari);
            timesheetResponse.setJamMsk(jamMsk);
            timesheetResponse.setJamPlg(jamPlg);
            timesheetResponse.setTotalJamKerja(totalJamKerja);
            timesheetResponse.setOvertime(overtime);
            timesheetResponse.setNotePekerjaan(notePekerjaan);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", timesheetResponse);
            
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
}
