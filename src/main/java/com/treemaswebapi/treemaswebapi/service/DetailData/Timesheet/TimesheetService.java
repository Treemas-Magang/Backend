package com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.entity.TimesheetEntity.TimesheetEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.TimesheetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimesheetService {
    private final TimesheetRepository timesheetRepository;
    private final KaryawanRepository karyawanRepository;
    private final JwtService jwtService;
    
    public ResponseEntity<Map<String, Object>> timesheetGet(
        @RequestHeader("Authorization") String jwtToken, 
        TimesheetResponse timesheetResponse
        ) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String nik = jwtService.extractUsername(token);

            Optional<KaryawanEntity> karyawan = karyawanRepository.findById(nik);
            String namaKaryawan = karyawan.get().getNama();

            Optional<TimesheetEntity> timesheet = timesheetRepository.findByNik(nik);
            String hari = timesheet.get().getHari();
            LocalDate tglAbsen = timesheet.get().getTglMsk();
            ProjectEntity projectId = timesheet.get().getProjectId();
            Time jamMsk = timesheet.get().getJamMasuk();
            Time jamPlg = timesheet.get().getJamKeluar();
            BigDecimal totalJamKerja = timesheet.get().getTotalJamKerja();
            // Menentukan jam kerja normal
            BigDecimal jamKerjaNormal = BigDecimal.valueOf(9);

            // Menghitung jam lembur (overtime)
            BigDecimal overtime = BigDecimal.ZERO;
            if (totalJamKerja != null) {
                // Only perform subtraction if totalJamKerja is not null
                overtime = totalJamKerja.subtract(jamKerjaNormal).max(BigDecimal.ZERO);
            }

            // Mengatur nilai overtime sebagai jam lembur (dibulatkan ke atas)
            int jamLembur = overtime.setScale(0, RoundingMode.CEILING).intValue();

            String notePekerjaan = timesheet.get().getNote();

            timesheetResponse.setNik(nik);
            timesheetResponse.setNamaKaryawan(namaKaryawan);
            timesheetResponse.setTglAbsen(tglAbsen);
            timesheetResponse.setProjectId(projectId);
            timesheetResponse.setHari(hari);
            timesheetResponse.setJamMsk(jamMsk);
            timesheetResponse.setJamPlg(jamPlg);
            timesheetResponse.setTotalJamKerja(totalJamKerja);
            timesheetResponse.setOvertime(jamLembur);
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
