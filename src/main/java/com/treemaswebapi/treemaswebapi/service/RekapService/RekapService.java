package com.treemaswebapi.treemaswebapi.service.RekapService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseEntity;
import com.treemaswebapi.treemaswebapi.entity.TimesheetEntity.TimesheetEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.ClaimRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseRepository;
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
    private final ClaimRepository claimRepository;

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
                    reimburseResponse.setFlgKet(dataReimbursenya.getKeterangan());
                    reimburseResponse.setHari(dataReimbursenya.getHari());
                    reimburseResponse.setJamMsk(dataReimbursenya.getJamMsk());
                    reimburseResponse.setJamPlg(dataReimbursenya.getJamPlg());
                    reimburseResponse.setLokasi(dataReimbursenya.getProjectId().getLokasi());
                    reimburseResponse.setNamaProject(dataReimbursenya.getProjectId().getNamaProject());
                    reimburseResponse.setTanggal(dataReimbursenya.getTglAbsen());
                    reimburseResponse.setTransport(dataReimbursenya.getProjectId().getBiayaReimburse());

                    long uangMakanValue = "1".equals(dataReimbursenya.getIsLembur()) ? 20000L : 0L;
                    reimburseResponse.setUangMakan(uangMakanValue);
                    

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
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Reimburse for nik: "+nik+" with idReimburse "+id+" retrieved successfully");
                    response.put("data", dataReimbursenya);
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
    
                List<CutiEntity> dataCutinya = cutiRepository.findAllByNikAndFlgKet(nik, "cuti");
    
                if (!dataCutinya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Cuti for nik: "+nik+" retrieved successfully");
                    response.put("data", dataCutinya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Cuti found for nik :" + nik);
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

    public ResponseEntity<Map<String, Object>> rekapCutiDetail (@RequestHeader String tokenWithBearer, @RequestParam Long id) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                Optional<CutiEntity> dataCutinya = cutiRepository.findById(id);
    
                if (!dataCutinya.isEmpty()) {
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
    
                List<CutiEntity> dataSakitnya = cutiRepository.findAllByNikAndFlgKet(nik, "sakit");
    
                if (!dataSakitnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Sakit for nik: "+nik+" retrieved successfully");
                    response.put("data", dataSakitnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Sakit found for nik :" + nik);
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
    
                Optional<CutiEntity> dataSakitnya = cutiRepository.findById(id);
    
                if (!dataSakitnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Sakit for nik: "+nik+" with idSakit "+id+" retrieved successfully");
                    response.put("data", dataSakitnya);
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
    
                List<ClaimEntity> dataClaimnya = claimRepository.findAllByNik(nik);
    
                if (!dataClaimnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Claim for nik: "+nik+" retrieved successfully");
                    response.put("data", dataClaimnya);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Data Claim found for nik :" + nik);
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
    
                Optional<ClaimEntity> dataClaimnya = claimRepository.findById(id);
    
                if (!dataClaimnya.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Data Claim for nik: "+nik+" with idClaim "+id+" retrieved successfully");
                    response.put("data", dataClaimnya);
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
}
