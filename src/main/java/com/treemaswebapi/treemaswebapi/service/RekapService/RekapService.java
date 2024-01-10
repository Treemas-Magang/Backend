package com.treemaswebapi.treemaswebapi.service.RekapService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.RekapController.TimesheetRequest;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimEntity;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimImageEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiImageAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiImageEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.entity.TimesheetEntity.TimesheetEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.ClaimImageRepository;
import com.treemaswebapi.treemaswebapi.repository.ClaimRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiImageAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiImageRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;
import com.treemaswebapi.treemaswebapi.repository.TimesheetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RekapService {
    private final AbsenRepository absenRepository;
    private final JwtService jwtService;

    private final ReimburseAppRepository reimburseAppRepository;
    private final TimesheetRepository timesheetRepository;
    private final CutiRepository cutiRepository;
    private final CutiAppRepository cutiAppRepository;
    private final ClaimRepository claimRepository;
    private final KaryawanRepository karyawanRepository;
    private final CutiImageRepository cutiImageRepository;
    private final ClaimImageRepository claimImageRepository;
    private final CutiImageAppRepository cutiImageAppRepository;
    private final SysUserRepository sysUserRepository;

    /* --------------------------------------------BAGIAN REIMBURSE------------------------------------------------ */
    public ResponseEntity<Map<String, Object>> rekapReimburse(@RequestHeader String tokenWithBearer) {
    try {
        if (tokenWithBearer.startsWith("Bearer ")) {
            String token = tokenWithBearer.substring("Bearer ".length());
            String nik = jwtService.extractUsername(token);

            List<ReimburseAppEntity> data2Reimbursenya = reimburseAppRepository.findAllByNik(nik);

            if (!data2Reimbursenya.isEmpty()) {
                List<ReimburseResponse> reimburseResponses = new ArrayList<>();
                for (ReimburseAppEntity dataReimbursenya : data2Reimbursenya) {
                    ReimburseResponse reimburseResponse = new ReimburseResponse();
                    reimburseResponse.setId(dataReimbursenya.getId());
                    reimburseResponse.setFlgKet(dataReimbursenya.getKeterangan());
                    reimburseResponse.setHari(dataReimbursenya.getHari());
                    reimburseResponse.setJamMsk(dataReimbursenya.getJamMsk());
                    reimburseResponse.setJamPlg(dataReimbursenya.getJamPlg());
                    String lokasi = Optional.ofNullable(dataReimbursenya.getProjectId())
                            .map(projectId -> projectId.getLokasi())
                            .orElse(null);
                    reimburseResponse.setLokasi(lokasi);
                    String namaProject = Optional.ofNullable(dataReimbursenya.getProjectId())
                            .map(projectId -> projectId.getNamaProject())
                            .orElse(null);
                    reimburseResponse.setNamaProject(namaProject);
                    reimburseResponse.setTanggal(dataReimbursenya.getTglAbsen());
                    BigDecimal biayaReimburse = Optional.ofNullable(dataReimbursenya.getProjectId())
                            .map(projectId -> projectId.getBiayaReimburse())
                            .orElse(null);
                    reimburseResponse.setTransport(biayaReimburse);
                    
                    LocalTime jamMasuk = dataReimbursenya.getJamMsk();
                    LocalTime jamPulang = dataReimbursenya.getJamPlg();
                    
                    BigDecimal totalHours = BigDecimal.valueOf(0D);
                    if (jamMasuk != null && jamPulang !=null) {
                        Duration duration = Duration.between(jamMasuk, jamPulang);
                        Double hours = duration.getSeconds() / 3600.0;
                        totalHours = BigDecimal.valueOf(hours);
                    }else{
                        totalHours = BigDecimal.valueOf(0D);                        
                    }
                    reimburseResponse.setTotalJamKerja(totalHours);
                    long uangMakanValue = "1".equals(dataReimbursenya.getIsLembur()) ? 20000L : 0L;
                    reimburseResponse.setUangMakan(uangMakanValue);
                    
                    BigDecimal regularHours = BigDecimal.valueOf(9);
                    BigDecimal overtimeHours = BigDecimal.valueOf(0);
                    if(totalHours.compareTo(regularHours) > 0){
                     overtimeHours = totalHours.subtract(regularHours);
                    }else{
                     overtimeHours = BigDecimal.valueOf(0D);
                    }
                    reimburseResponse.setOvertime(overtimeHours);
                    

                    String status = "1".equals(dataReimbursenya.getIsApprove()) ? "Approved" :
                    "0".equals(dataReimbursenya.getIsApprove()) ? "Not Approved" : "Waiting for Approval";
                    reimburseResponse.setStatus(status);


                    reimburseResponses.add(reimburseResponse);
                }

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Data Reimburse for nik: " + nik + " retrieved successfully");
                response.put("data", reimburseResponses);
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "No Data Reimburse found for nik: " + nik);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid token format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    } catch (Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Failed to retrieve Data Reimburse");
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
    public ResponseEntity<Map<String, Object>> rekapReimburseDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<ReimburseAppEntity> dataReimbursenya = reimburseAppRepository.findById(id);
    
                if (!dataReimbursenya.isEmpty()) {
                    ReimburseResponse reimburseResponse = new ReimburseResponse();
                    reimburseResponse.setId(dataReimbursenya.get().getId());
                    reimburseResponse.setFlgKet(dataReimbursenya.get().getKeterangan());
                    reimburseResponse.setHari(dataReimbursenya.get().getHari());
                    reimburseResponse.setJamMsk(dataReimbursenya.get().getJamMsk());
                    reimburseResponse.setJamPlg(dataReimbursenya.get().getJamPlg());
                    String lokasi = Optional.ofNullable(dataReimbursenya.get().getProjectId())
                            .map(projectId -> projectId.getLokasi())
                            .orElse(null);
                    reimburseResponse.setLokasi(lokasi);
                    String namaProject = Optional.ofNullable(dataReimbursenya.get().getProjectId())
                            .map(projectId -> projectId.getNamaProject())
                            .orElse(null);
                    reimburseResponse.setNamaProject(namaProject);
                    reimburseResponse.setTanggal(dataReimbursenya.get().getTglAbsen());
                    BigDecimal biayaReimburse = Optional.ofNullable(dataReimbursenya.get().getProjectId())
                            .map(projectId -> projectId.getBiayaReimburse())
                            .orElse(null);
                    reimburseResponse.setTransport(biayaReimburse);
                    
                    LocalTime jamMasuk = dataReimbursenya.get().getJamMsk();
                    LocalTime jamPulang = dataReimbursenya.get().getJamPlg();
                    Duration duration = Duration.between(jamMasuk, jamPulang);
                    Double hours = duration.getSeconds() / 3600.0;
                    BigDecimal totalHours = BigDecimal.valueOf(hours);
                    reimburseResponse.setTotalJamKerja(totalHours);

                    long uangMakanValue = "1".equals(dataReimbursenya.get().getIsLembur()) ? 20000L : 0L;
                    reimburseResponse.setUangMakan(uangMakanValue);
                    
                    BigDecimal regularHours = BigDecimal.valueOf(9);
                    BigDecimal overtimeHours = BigDecimal.valueOf(0);
                    if(totalHours.compareTo(regularHours) > 0){
                     overtimeHours = totalHours.subtract(regularHours);
                    }
                    reimburseResponse.setOvertime(overtimeHours);
                    

                    String status = "1".equals(dataReimbursenya.get().getIsApprove()) ? "Approved" :
                    "0".equals(dataReimbursenya.get().getIsApprove()) ? "Not Approved" : "Waiting for Approval";
                    reimburseResponse.setStatus(status);


                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Reimburse for nik: "+nik+" with idReimburse "+id+" retrieved successfully");
                    response.put("data", reimburseResponse);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Reimburse found for nik :" + nik + "and idReimburse " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Reimburse");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getItunganReimburse (@RequestHeader String tokenWithBearer) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                SysUserEntity dataUser = sysUserRepository.findByUserId(nik).get();
                String jabatan = dataUser.getRole().getJabatanId();
                LocalDate currentDate = LocalDate.now();
                System.out.println("currentDate: "+currentDate);
                // Calculate the start date as the 13th of the current month
                LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 13);
                System.out.println("startDate: "+startDate);
                // Calculate the end date as the 13th of the next month
                LocalDate endDate = startDate.plusMonths(1);
                System.out.println("endDate: "+endDate);
                List<ReimburseAppEntity> data2Reimbursenya = reimburseAppRepository.findByNikAndTglAbsenBetween(nik, startDate, endDate);
                BigDecimal totalReimburse = BigDecimal.ZERO;
                BigDecimal totalClaim = BigDecimal.ZERO;
                BigDecimal totalDuit = BigDecimal.ZERO;
                BigDecimal dataVoice = BigDecimal.ZERO;
                switch (jabatan) {
                    case "HEAD":
                    case "LEAD":
                        dataVoice = BigDecimal.valueOf(150000);
                        break;
            
                    case "EMPL":
                        dataVoice = BigDecimal.valueOf(100000);
                        break;
                    default:
                        dataVoice = BigDecimal.ZERO;
                        break;
                }
                List<ClaimEntity> data2Claimnya = claimRepository.findByNikAndTanggalBetween(nik, startDate, endDate);
                if (!data2Reimbursenya.isEmpty()) {
                    Map<LocalDate, BigDecimal> reimburseHarian = new HashMap<>();
                    
                    for(ReimburseAppEntity dataReimbursenya : data2Reimbursenya)
                        {
                            if ("1".equals(dataReimbursenya.getIsApprove())) 
                            {
                                BigDecimal uangMakan = "1".equals(dataReimbursenya.getIsLembur()) ? new BigDecimal("20000") : BigDecimal.ZERO;

                                LocalDate tanggal = dataReimbursenya.getTglAbsen();
                                BigDecimal duitnya = dataReimbursenya.getProjectId().getBiayaReimburse();
                                reimburseHarian.put(tanggal, reimburseHarian.getOrDefault(tanggal, BigDecimal.ZERO).add(duitnya));

                                totalReimburse = totalReimburse.add(duitnya);
                                totalReimburse = totalReimburse.add(uangMakan);
                            } 
                        }
                    if (!data2Claimnya.isEmpty()) {
                        for(ClaimEntity dataClaimnya : data2Claimnya)
                            {
                                BigDecimal duitClaimnya = dataClaimnya.getNominal();
                                totalClaim = totalClaim.add(duitClaimnya);
                            }
                    }
                    totalDuit = totalDuit.add(dataVoice);
                    totalDuit = totalDuit.add(totalClaim);
                    totalDuit = totalDuit.add(totalReimburse);

                    TotalReimburse totalReimburseResponse = TotalReimburse.builder()
                    .dataVoice(dataVoice)
                    .lainlain(totalClaim)
                    .reimburse(totalReimburse)
                    .keseluruhan(totalDuit)
                    .build();
                    

                    response.put("success", true);
                    response.put("message", "berhasil retrieve listReimbursenya");
                    response.put("data",totalReimburseResponse);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else {
                    response.put("success", false);
                    response.put("message", "No Data Reimburse found for nik :" + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Reimburse");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /* --------------------------------------------BAGIAN TIMESHEET------------------------------------------------ */
     public ResponseEntity<Map<String, Object>> rekapTimesheet(@RequestHeader String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                List<TimesheetEntity> dataTimesheetnya = timesheetRepository.findAllByNik(nik);
    
                if (!dataTimesheetnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Timesheet for nik: "+nik+" retrieved successfully");
                    response.put("data", dataTimesheetnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Timesheet found for nik :" + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Timesheet");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> rekapTimesheetDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<TimesheetEntity> dataTimesheetnya = timesheetRepository.findById(id);
    
                if (!dataTimesheetnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Timesheet for nik: "+nik+" with idTimesheet "+id+" retrieved successfully");
                    response.put("data", dataTimesheetnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Timesheet found for nik :" + nik + "and idTimesheet " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Timesheet");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    public ResponseEntity<Map<String, Object>> rekapTimesheetUpdate (
        @RequestHeader String tokenWithBearer, 
        @RequestParam Long id,
        @RequestBody TimesheetRequest noteTimesheet
    ){
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<TimesheetEntity> dataTimesheetnya = timesheetRepository.findById(id);
    
                if (!dataTimesheetnya.isEmpty()) {
                    // ambil datanya, karena Optional
                    TimesheetEntity datanya = dataTimesheetnya.get();
                    // ubah data keterangannnya
                    datanya.setNote(noteTimesheet.getNoteTimesheet());
                    timesheetRepository.save(datanya);

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Note Timesheet for nik: "+nik+" with idTimesheet "+id+" has been put successfully");
                    response.put("data", datanya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Timesheet found for nik :" + nik + "and idTimesheet " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Timesheet");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public BigDecimal calculateJamKerja(LocalTime jamMasuk, LocalTime jamKeluar) {
        // Check for null values or handle invalid cases
        if (jamMasuk == null || jamKeluar == null) {
            return BigDecimal.ZERO;
        }

        // Calculate the duration in hours
        long hours = ChronoUnit.HOURS.between(jamMasuk, jamKeluar);

        // Convert the result to BigDecimal
        return BigDecimal.valueOf(hours);
    }
    
    public ResponseEntity<Map<String, Object>> getItunganTimesheet (@RequestHeader String tokenWithBearer) { 
        try {
            Map<String, Object> response = new HashMap<>();
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                List<TimesheetEntity> data2Timesheetnya = timesheetRepository.findAllByNik(nik);
                
                if (!data2Timesheetnya.isEmpty()) {
                    BigDecimal totalJamReguler = data2Timesheetnya.stream()
                    .map((TimesheetEntity timesheet) -> calculateJamKerja(timesheet.getJamMasuk(), timesheet.getJamKeluar()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalJamLembur = totalJamReguler.compareTo(BigDecimal.valueOf(9)) > 0 ?
                            totalJamReguler.subtract(BigDecimal.valueOf(9)) : BigDecimal.ZERO;

                    BigDecimal totalJamKerja = totalJamReguler.add(totalJamLembur);
                    
                    TimesheetSummary timesheetSummary = TimesheetSummary.builder()
                    .totalJamKerja(totalJamKerja)
                    .totalJamLembur(totalJamLembur)
                    .totalJamReguler(totalJamReguler)
                    .build();

                    response.put("success", true);
                    response.put("message", "berhasil");
                    response.put("data",timesheetSummary);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else {
                    response.put("success", false);
                    response.put("message", "No Data Reimburse found for nik :" + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Timesheet");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }   

    /* --------------------------------------------BAGIAN ABSEN------------------------------------------------ */
     public ResponseEntity<Map<String, Object>> rekapAbsen(@RequestHeader String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                List<AbsenEntity> dataAbsennya = absenRepository.findAllByNik(nik);
    
                if (!dataAbsennya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Absen for nik: "+nik+" retrieved successfully");
                    response.put("data", dataAbsennya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Absen found for nik :" + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Absen");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> rekapAbsenDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<AbsenEntity> dataAbsennya = absenRepository.findById(id);
    
                if (!dataAbsennya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Absen for nik: "+nik+" with idAbsen "+id+" retrieved successfully");
                    response.put("data", dataAbsennya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Absen found for nik :" + nik + "and idAbsen " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Absen");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /* --------------------------------------------BAGIAN CUTI------------------------------------------------ */
    public ResponseEntity<Map<String, Object>> rekapCuti(@RequestHeader String tokenWithBearer) {
        try {
        if (tokenWithBearer.startsWith("Bearer ")) {
            String token = tokenWithBearer.substring("Bearer ".length());
            String nik = jwtService.extractUsername(token);
            System.out.println("ini masuk area rekap cuti");
            List<CutiAppEntity> data2Cutinya = cutiAppRepository.findAllByNikAndFlgKet(nik,"cuti");
            System.out.println("ini data2cutinya:"+data2Cutinya);
            
            if (!data2Cutinya.isEmpty()) {
                List<CutiResponse> response2Cutinya = new ArrayList<>();
                for (CutiAppEntity dataCutinya : data2Cutinya) {
                    CutiResponse cutiResponse = new CutiResponse();
                    cutiResponse = CutiResponse.builder()
                    .id(dataCutinya.getId())
                    .alamatCuti(dataCutinya.getAlamatCuti())
                    .isApproved(dataCutinya.getIsApproved())
                    .jenisCuti(dataCutinya.getJenisCuti())
                    .jmlCuti(dataCutinya.getJmlCuti())
                    .jmlCutiBersama(dataCutinya.getJmlCutiBersama())
                    .jmlCutiKhusus(dataCutinya.getJmlCutiKhusus())
                    .keperluanCuti(dataCutinya.getKeperluanCuti())
                    .nama(dataCutinya.getNama())
                    .nik(dataCutinya.getNik())
                    .sisaCuti(dataCutinya.getSisaCuti())
                    .tglKembaliKerja(dataCutinya.getTglKembaliKerja())
                    .tglMulai(dataCutinya.getTglMulai())
                    .tglSelesai(dataCutinya.getTglSelesai())
                    .build();
                    
                    String status = "1".equals(dataCutinya.getIsApproved()) ? "Approved" :
                    "0".equals(dataCutinya.getIsApproved()) ? "Not Approved" : "Waiting for Approval";
                    cutiResponse.setStatus(status);


                    response2Cutinya.add(cutiResponse);
                }

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Data Cuti for nik: " + nik + " retrieved successfully");
                response.put("data", data2Cutinya);
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "No Data Cuti found for nik: " + nik);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid token format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    } catch (Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Failed to retrieve Data Cuti");
        response.put("error", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    }

    public ResponseEntity<Map<String, Object>> rekapCutiDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                String nama = karyawanRepository.findNamaByNik(nik);
                Optional<CutiAppEntity> dataCutinya = cutiAppRepository.findById(id);
    
                if (!dataCutinya.isEmpty()) {
                    CutiResponse cutiResponse = new CutiResponse();
                    cutiResponse = CutiResponse.builder()
                    .id(dataCutinya.get().getId())
                    .alamatCuti(dataCutinya.get().getAlamatCuti())
                    .isApproved(dataCutinya.get().getIsApproved())
                    .jenisCuti(dataCutinya.get().getJenisCuti())
                    .jmlCuti(dataCutinya.get().getJmlCuti())
                    .jmlCutiBersama(dataCutinya.get().getJmlCutiBersama())
                    .jmlCutiKhusus(dataCutinya.get().getJmlCutiKhusus())
                    .keperluanCuti(dataCutinya.get().getKeperluanCuti())
                    .nama(dataCutinya.get().getNama())
                    .nik(dataCutinya.get().getNik())
                    .sisaCuti(dataCutinya.get().getSisaCuti())
                    .tglKembaliKerja(dataCutinya.get().getTglKembaliKerja())
                    .tglMulai(dataCutinya.get().getTglMulai())
                    .tglSelesai(dataCutinya.get().getTglSelesai())
                    .build();

                    String status = "1".equals(dataCutinya.get().getIsApproved()) ? "Approved" :
                    "0".equals(dataCutinya.get().getIsApproved()) ? "Not Approved" : "Waiting for Approval";
                    cutiResponse.setStatus(status);

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Cuti for nik: "+nik+" with idCuti "+id+" retrieved successfully");
                    response.put("data", dataCutinya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Cuti found for nik :" + nik + "and idCuti " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Cuti");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /* --------------------------------------------BAGIAN SAKIT------------------------------------------------ */
    public ResponseEntity<Map<String, Object>> rekapSakit(@RequestHeader String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                Boolean ketemu = false;
                List<CutiAppEntity> data2AppSakitnya = cutiAppRepository.findAllByNikAndFlgKet(nik, "sakit");
                if (!data2AppSakitnya.isEmpty()) {
                    ketemu = true;
                }
                System.out.println("ada ngga?" + "" + ketemu);
                for (CutiAppEntity dataSakitnya : data2AppSakitnya) {
                    Long idCuti = dataSakitnya.getId();
                    Optional<CutiImageAppEntity> cutiImage = cutiImageAppRepository.findById(idCuti);
    
                    boolean gambarnya = false;
                    if (cutiImage.isPresent() && !cutiImage.get().getImage().isEmpty()) {
                        gambarnya = true;
                    }
                    dataSakitnya.setGambarnya(gambarnya);
                }
    
                if (!data2AppSakitnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Sakit for nik: " + nik + " retrieved successfully");
                    response.put("data", data2AppSakitnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Sakit found for nik: " + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Sakit");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> rekapSakitDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<CutiImageAppEntity> gambarSakitnya = cutiImageAppRepository.findById(id);
    
                if (!gambarSakitnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Gambar Sakit for nik: "+nik+" with idSakit "+id+" retrieved successfully");
                    response.put("data", gambarSakitnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Sakit found for nik :" + nik + "and idSakit " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Sakit");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /* --------------------------------------------BAGIAN CLAIM------------------------------------------------ */
    public ResponseEntity<Map<String, Object>> rekapClaim(@RequestHeader String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                List<ClaimEntity> data2Claimnya = claimRepository.findAllByNik(nik);
    
                for (ClaimEntity dataClaimnya : data2Claimnya) {
                    Long idClaim = dataClaimnya.getId();
                    Optional<ClaimImageEntity> claimImage = claimImageRepository.findById(idClaim);
    
                    boolean gambarnya = false;
                    if (claimImage.isPresent() && !claimImage.get().getImage64().isEmpty()) {
                        gambarnya = true;
                    }
                    dataClaimnya.setGambarnya(gambarnya);
                }
    
                if (!data2Claimnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Claim for nik: " + nik + " retrieved successfully");
                    response.put("data", data2Claimnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Claim found for nik: " + nik);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Claim");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

    public ResponseEntity<Map<String, Object>> rekapClaimDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<ClaimImageEntity> gambarClaimnya = claimImageRepository.findById(id);
    
                if (!gambarClaimnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Claim for nik: "+nik+" with idClaim "+id+" retrieved successfully");
                    response.put("data", gambarClaimnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Claim found for nik :" + nik + "and idClaim " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token format is invalid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Data Claim");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private static String getIndonesianDayOfWeek(DayOfWeek dayOfWeek){
        Map<String,String> indonesianDayMap = new HashMap<>();
        indonesianDayMap.put("MONDAY", "Senin");
        indonesianDayMap.put("TUESDAY", "Selasa");
        indonesianDayMap.put("WEDNESDAY", "Rabu");
        indonesianDayMap.put("THURSDAY", "Kamis");
        indonesianDayMap.put("FRIDAY", "Jumat");
        indonesianDayMap.put("SATURDAY", "Sabtu");
        indonesianDayMap.put("SUNDAY", "Minggu");

        return indonesianDayMap.get(dayOfWeek.toString());
    }

}
