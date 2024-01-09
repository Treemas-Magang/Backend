package com.treemaswebapi.treemaswebapi.service.Upload.Absen;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenImgEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenTrackingEntity;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenTrackingRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadAbsenService {
    private final JwtService jwtService;
    private final AbsenRepository absenRepository;
    private final KaryawanRepository karyawanRepository;
    private final PenempatanRepository penempatanRepository;
    private final ProjectRepository projectRepository;
    private final AbsenTrackingRepository absenTrackingRepository;
    private final AbsenAppUploadRepository absenAppUploadRepository;

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

    public ResponseEntity<Map<String, Object>> getAbsenSendiriWeb(@RequestHeader("Authorization") String jwtToken) {
        try {
            if (jwtToken.startsWith("Bearer ")) {
                String token = jwtToken.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                
                // ABSEN YANG KETERIMA, DITOLAK, MENUNGGU DARI WEB SAJA
                List<AbsenAppUploadEntity> dataAbsennya = absenAppUploadRepository.findAllByNik(nik);
                

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

    public ResponseEntity<Map<String, Object>> inputAbsenWeb(@RequestHeader("Authorization") String jwtToken, AbsenRequest request) {
        try {
            
            if (jwtToken.startsWith("Bearer ")) {
                String token = jwtToken.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                System.out.println(nik);
                String nama = karyawanRepository.findNamaByNik(request.getNik());
                if (nik != null) {
                    LocalDate tanggalIni = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
                    
                    System.out.println(nama);
                    if (nama.isEmpty()) {
                        // NIK not found in KaryawanEntity
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "NIK not found in KaryawanEntity. Please contact the administrator.");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    List<PenempatanEntity> registeredProjects = penempatanRepository.findAllByNik(nik);
                    // ini buat ngecek penempatan entitinya, ada apa engga dia
                    boolean projectFound = false;
                    String projectRequest = request.getProjectIdWeb();
                    for (PenempatanEntity projReq : registeredProjects) {
                        if (projReq.getProjectId().getProjectId().equals(projectRequest)) {
                            projectFound = true;
                            // break;
                        }
                    }
                    System.out.println(projectFound);
                    if (projectFound) {
                        ProjectEntity project = projectRepository.findByProjectId(projectRequest);
                        if (project != null) {
                            // Save to absen Entity
                            LocalTime jamSekarang = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                            AbsenAppUploadEntity absenEntity = absenAppUploadRepository.findByTglAbsenAndNik(tanggalIni, nik);
                            absenEntity.setProjectId(project);
                            absenEntity.setNik(request.getNik());
                            absenEntity.setNama(nama);
                            absenEntity.setDtmCrt(Timestamp.valueOf(LocalDateTime.now()));
                            absenEntity.setHari(getIndonesianDayOfWeek(LocalDate.now().getDayOfWeek()));
                            absenEntity.setLokasiMsk(request.getLokasiMsk());
                            absenEntity.setJamMsk(request.getJamMsk());
                            absenEntity.setTglAbsen(request.getTglAbsen());
                            absenEntity.setNoteTelatMsk(request.getNoteTelatMsk());
                            absenEntity.setNotePekerjaan(request.getNotePekerjaan());
                            absenEntity.setNotePlgCepat(request.getNotePlgCepat());
                            absenEntity.setIsWfh(request.getIsWfh());
                            absenAppUploadRepository.save(absenEntity);
                            System.out.println("sekarang tuh jam segini +" + jamSekarang);
                            // Save to absentrackingentity
                            AbsenTrackingEntity absenTrackingEntity = new AbsenTrackingEntity();
                            absenTrackingEntity.setId(absenEntity.getId());
                            absenTrackingEntity.setProjectId(project);
                            absenTrackingEntity.setNik(nik);
                            absenTrackingEntity.setNama(nama);
                            absenTrackingEntity.setHari(getIndonesianDayOfWeek(LocalDate.now().getDayOfWeek()));
                            absenTrackingEntity.setLokasiMsk(request.getLokasiMsk());
                            absenTrackingEntity.setJamMsk(request.getJamMsk());
                            absenTrackingEntity.setTglAbsen(request.getTglAbsen());
                            absenTrackingEntity.setNoteTelatMsk(request.getNoteTelatMsk());
                            absenTrackingEntity.setNotePekerjaan(request.getNotePekerjaan());
                            absenTrackingEntity.setNotePlgCepat(request.getNotePlgCepat());
                            absenTrackingEntity.setIsWfh(request.getIsWfh());

                            absenTrackingRepository.save(absenTrackingEntity);
    
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", true);
                            response.put("message", "Absen data inserted successfully");
                            response.put("data", absenEntity);
    
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } else {
                            // Project not found
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", false);
                            response.put("message", "Project not found");
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                        }
                    } else {
                        // User is not authorized to access this project
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "You are not authorized to access this project");
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                    }
                }
            } else {
                // Invalid token format
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            // Error occurred
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to insert absen data");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
        // Default return statement for unexpected cases
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
