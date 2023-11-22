package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenImgEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenResponse;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenTrackingEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectDetails;
import com.treemaswebapi.treemaswebapi.repository.AbsenImgRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenTrackingRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenService {

    private final JwtService jwtService;
    private final PenempatanRepository penempatanRepository;
    private final ProjectRepository projectRepository;
    private final AbsenRepository absenRepository;
    private final AbsenTrackingRepository absenTrackingRepository;
    private final AbsenImgRepository absenImgRepository;
    private final KaryawanRepository karyawanRepository;

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

    public ResponseEntity<Map<String, Object>> getProjectDetails(String tokenWithBearer) {
        List<ProjectDetails> projectDetails = new ArrayList<>();
    
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                List<PenempatanEntity> penempatanEntities = penempatanRepository.findByNik(nik);
    
                for (PenempatanEntity penempatanEntity : penempatanEntities) {
                    String projectId = penempatanEntity.getProjectId().toString();
                    ProjectEntity project = projectRepository.findByProjectId(projectId);
                    if (project != null) {
                        ProjectDetails projectDetail = new ProjectDetails();
                        projectDetail.setProjectId(projectId.toString());
                        projectDetail.setProjectName(project.getNamaProject());
                        projectDetail.setProjectAddress(project.getLokasi());
                        projectDetails.add(projectDetail);
                    }
                }
    
                Map<String, Object> response = new HashMap<>();
                if (!projectDetails.isEmpty()) {
                    response.put("success", true);
                    response.put("message", "Project details retrieved successfully");
                    response.put("data", projectDetails);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    response.put("success", false);
                    response.put("message", "No projects found for the user");
                    response.put("data", null);
                    response.put("nik", nik);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Failed to retrieve project details");
            response.put("error", e.getMessage());
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
        // In case of any exception or invalid token format, return an appropriate response
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Failed to retrieve project details");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    
    public ResponseEntity<Map<String, Object>> updateAbsen(@RequestHeader String tokenWithBearer, @RequestParam("date") LocalDate currentDate, @RequestBody AbsenRequest updateReq) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                String nama = karyawanRepository.findNamaByNik(nik);
                Map<String, Object> response = new HashMap<>();
                List<AbsenEntity> absenEntities = absenRepository.findByNikAndTglAbsen(nik, currentDate);
                // jam masuk jangan di rubah di tbl_absen, pokonya tbl_absen di-overwrite tapi tbl_absen_tracking di create 
                // yang dirubah di long/latMsk, alamatMsk, namaProject, projectId, jarakMsk, jarakMax dari tbl_project
                if (!absenEntities.isEmpty()) {
                    for (AbsenEntity absenEntity : absenEntities) {
                        absenEntity.setJarakMsk(updateReq.getJarakMsk());
                        absenEntity.setGpsLongitudeMsk(updateReq.getGpsLongitudeMsk());
                        absenEntity.setGpsLatitudeMsk(updateReq.getGpsLatitudeMsk());
                        absenEntity.setLokasiMsk(updateReq.getLokasiMsk());
                        absenEntity.setUsrCrt(nama);
                        // save ke absenEntity
                        absenRepository.save(absenEntity);
                        absenEntities.add(absenEntity);
                    }   
                    response.put("success", true);
                    response.put("message", "berhasil mengupdate absen");
                    response.put("data", absenEntities);
                }
            }else{
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "TOKEN ANDA TIDAK VALID");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Terjadi masalah");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Terjadi masalah");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    // masih error, ga ngeluarin PenempatanEntity / ProjectEntity
    public ResponseEntity<Map<String, Object>> inputAbsen(@RequestHeader("Authorization") String tokenWithBearer, AbsenRequest request) {
        try {
            
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                System.out.println(nik);
                if (nik != null) {
                    String nama = karyawanRepository.findNamaByNik(nik);
                    System.out.println(nama);
                    if (nama.isEmpty()) {
                        // NIK not found in KaryawanEntity
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "NIK not found in KaryawanEntity. Please contact the administrator.");
                        response.put("niknya", nik);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    List<PenempatanEntity> registeredProjects = penempatanRepository.findAllByNik(nik);
                    // ini buat ngecek penempatan entitinya, ada apa engga dia
                    System.out.println("Kesini  " + registeredProjects);
                    boolean projectFound = false;
                    String projectRequest = request.getProjectId().getProjectId().toString();
                    System.out.println("kesini 2  " + projectRequest);
                    System.out.println("kesini 3  " + registeredProjects.get(0).getProjectId());
                    for (PenempatanEntity projReq : registeredProjects) {
                        if (projReq.getProjectId().getProjectId().equals(projectRequest)) {
                            projectFound = true;
                            // break;
                        }
                    }
                    System.out.println(projectFound);
                    if (projectFound) {
                        ProjectEntity project = projectRepository.findByProjectId(projectRequest);
                        long currentTimeMillis = System.currentTimeMillis();
                        Timestamp dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));
                        if (project != null) {
                            // Save to absen Entity
                            AbsenEntity absenEntity = new AbsenEntity();
                            absenEntity.setProjectId(project);
                            absenEntity.setNik(nik);
                            absenEntity.setNama(nama);
                            absenEntity.setDtmCrt(dtmCrt);
                            absenEntity.setHari(getIndonesianDayOfWeek(LocalDate.now().getDayOfWeek()));
                            absenEntity.setLokasiMsk(request.getLokasiMsk());
                            absenEntity.setJamMsk(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            absenEntity.setJarakMsk(request.getJarakMsk());
                            absenEntity.setTglAbsen(LocalDate.now());
                            absenEntity.setNoteTelatMsk(request.getNoteTelatMsk());
                            absenEntity.setGpsLatitudeMsk(request.getGpsLatitudeMsk());
                            absenEntity.setGpsLongitudeMsk(request.getGpsLongitudeMsk());
    
                            absenEntity = absenRepository.save(absenEntity);

                            // Save to absentrackingentity
                            AbsenTrackingEntity absenTrackingEntity = new AbsenTrackingEntity();
                            absenTrackingEntity.setProjectId(project);
                            absenTrackingEntity.setNik(nik);
                            absenTrackingEntity.setNama(nama);
                            absenTrackingEntity.setHari(getIndonesianDayOfWeek(LocalDate.now().getDayOfWeek()));
                            absenTrackingEntity.setLokasiMsk(request.getLokasiMsk());
                            absenTrackingEntity.setJamMsk(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            absenTrackingEntity.setJarakMsk(request.getJarakMsk());
                            absenTrackingEntity.setTglAbsen(LocalDate.now());
                            absenTrackingEntity.setNoteTelatMsk(request.getNoteTelatMsk());
                            absenTrackingEntity.setGpsLatitudeMsk(request.getGpsLatitudeMsk());
                            absenTrackingEntity.setGpsLongitudeMsk(request.getGpsLongitudeMsk());
                            absenTrackingRepository.save(absenTrackingEntity);
    
                            AbsenImgEntity absenImgEntity = new AbsenImgEntity();
                            absenImgEntity.setNik(nik);
                            absenImgEntity.setTglAbsen(LocalDate.now());
                            absenImgEntity.setImage64(request.getPhotoAbsen());
    
                            absenImgEntity = absenImgRepository.save(absenImgEntity);
    
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
        public ResponseEntity<Map<String, Object>> inputAbsenPulang(@RequestHeader("Authorization") String tokenWithBearer, AbsenRequest request) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")){
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                // Check if the user has already done the input for the current date
                LocalDate currentDate = LocalDate.now();
                List<AbsenEntity> existingAbsenRecords = absenRepository.findByNikAndTglAbsen(nik, currentDate);

                if (!existingAbsenRecords.isEmpty()) {
                    // User has already done the input-absen for the day
                    AbsenEntity existingAbsenEntity = existingAbsenRecords.get(0);
                    // Make separate response
                    // save ke absenEntity
                    existingAbsenEntity.setNotePekerjaan(request.getNotePekerjaan());
                    existingAbsenEntity.setGpsLatitudePlg(request.getGpsLatitudePlg());
                    existingAbsenEntity.setGpsLongitudePlg(request.getGpsLongitudePlg());
                    existingAbsenEntity.setLokasiPlg(request.getLokasiPlg());
                    existingAbsenEntity.setJarakPlg(request.getJarakPlg());
                    existingAbsenEntity.setJamPlg(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                    existingAbsenEntity.setNotePlgCepat(request.getNotePlgCepat());
                    LocalTime jamMsk = existingAbsenEntity.getJamMsk();
                    LocalTime jamPlg = existingAbsenEntity.getJamPlg();
                    if (jamMsk != null && jamPlg != null) {
                        BigDecimal hoursPart = new BigDecimal(Duration.between(jamMsk, jamPlg).toHoursPart());
                        existingAbsenEntity.setTotalJamKerja(hoursPart);
                        if (hoursPart.compareTo(new BigDecimal(9))>0) {
                            existingAbsenEntity.setIsAbsen("1");
                            existingAbsenEntity.setIsLembur("1");
                        } else {
                            existingAbsenEntity.setIsAbsen("1");
                            existingAbsenEntity.setIsLembur("0");
                        }
                    }
                    existingAbsenEntity = absenRepository.save(existingAbsenEntity);
                    // save ke absenTrackingEntity
                    AbsenTrackingEntity absenTrackingEntity = new AbsenTrackingEntity();
                    
                    absenTrackingEntity.setNotePekerjaan(request.getNotePekerjaan());
                    absenTrackingEntity.setGpsLatitudePlg(request.getGpsLatitudePlg());
                    absenTrackingEntity.setGpsLongitudePlg(request.getGpsLatitudePlg());
                    absenTrackingEntity.setLokasiPlg(request.getLokasiPlg());
                    absenTrackingEntity.setJarakPlg(request.getJarakPlg());
                    absenTrackingEntity.setJamPlg(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                    absenTrackingEntity.setNotePlgCepat(request.getNotePlgCepat());

                    absenTrackingEntity = absenTrackingRepository.save(absenTrackingEntity);


                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Absen data inserted successfully");
                    response.put("data", existingAbsenEntity);
    
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }else{
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "You have already submitted absen data for today");
                    response.put("data", existingAbsenRecords.get(0));
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to insert absen data");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    public ResponseEntity<Map<String, Object>> updateAbsen(@RequestHeader("Authorization") String tokenWithBearer, AbsenRequest request) {
        try {
            // Extracting NIK from the token
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                
                // Check if NIK is valid
                if (nik != null) {
                    // Retrieve KaryawanEntity by NIK
                    String karyawanEntities = karyawanRepository.findNamaByNik(nik);
                    
                    if (karyawanEntities.isEmpty()) {
                        // NIK not found in KaryawanEntity
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "NIK not found in KaryawanEntity. Please contact the administrator.");
                        response.put("niknya", nik);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    
                    // Extracting the user's name from KaryawanEntity (just in case)
                    String nama = karyawanEntities;
                    
                    // Retrieve registered projects for the user
                    List<PenempatanEntity> registeredProjects = penempatanRepository.findAllByNik(nik);
                    boolean projectFound = false;
                    
                    // Check if the requested project is among the registered projects
                    for (PenempatanEntity penempatanEntity : registeredProjects) {
                        if (penempatanEntity.getProjectId().getProjectId().equals(request.getProjectId().getProjectId().toString())) {
                            projectFound = true;
                            // break;
                        }
                    }
                    
                    if (projectFound) {
                        // Project found, proceed to update absen data
                        LocalDate currentDate = LocalDate.now();
                        List<AbsenEntity> existingAbsenEntityList = absenRepository.findByNikAndTglAbsen(nik, currentDate);
                        AbsenEntity existingAbsenEntity = existingAbsenEntityList.get(0);
                        AbsenTrackingEntity trackingAbsenEntity = new AbsenTrackingEntity();
                        if (!existingAbsenEntityList.isEmpty()) {
                            // Update the existing AbsenEntity
                            existingAbsenEntity.setJamMsk(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            existingAbsenEntity.setJarakMsk(request.getJarakMsk());
                            existingAbsenEntity.setGpsLatitudeMsk(request.getGpsLatitudeMsk());
                            existingAbsenEntity.setGpsLongitudeMsk(request.getGpsLongitudeMsk());
                            
                            // Save the updated AbsenEntity
                            absenRepository.save(existingAbsenEntity);
                            
                            // Submitting the updated value to the tbl_absen_tracking
                            trackingAbsenEntity.setDtmCrt(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            trackingAbsenEntity.setGpsLatitudeMsk(request.getGpsLatitudeMsk());
                            trackingAbsenEntity.setGpsLongitudeMsk(request.getGpsLongitudeMsk());
                            trackingAbsenEntity.setJamMsk(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            trackingAbsenEntity.setHari(getIndonesianDayOfWeek(LocalDate.now().getDayOfWeek()));
                            trackingAbsenEntity.setJarakMsk(request.getJarakMsk());
                            trackingAbsenEntity.setLokasiMsk(request.getLokasiMsk());
                            trackingAbsenEntity.setNama(nama);
                            trackingAbsenEntity.setNoteTelatMsk(request.getNoteTelatMsk());

                            // save it to the tracking table
                            absenTrackingRepository.save(trackingAbsenEntity);


                            Map<String, Object> response = new HashMap<>();
                            response.put("success", true);
                            response.put("message", "Absen data updated successfully");
                            response.put("data", trackingAbsenEntity);
                            
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } else {
                            // Absen data not found for the current date
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", false);
                            response.put("message", "Absen data not found for the current date");
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
            response.put("message", "Failed to update absen data");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
        // Default return statement for unexpected cases
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    public ResponseEntity<Map<String, Object>> getUnprocessedAbsen(String tokenWithBearer) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);

                // Search for idAbsen that has no Plg data
                List<AbsenEntity> unprocessedAbsenList = absenRepository.findIdAbsenByNikAndIsAbsenIsNull(nik);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Unprocessed Absen data retrieved successfully");
                response.put("data", unprocessedAbsenList);

                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve unprocessed Absen data");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}