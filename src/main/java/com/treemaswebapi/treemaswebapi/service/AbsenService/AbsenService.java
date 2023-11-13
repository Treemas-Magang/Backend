package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenImgEntity;
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

    public ResponseEntity<Map<String,Object>> getProjectDetails(String tokenWithBearer) {
        List<ProjectDetails> projectDetails = new ArrayList<>(); // Declare projectDetails outside the if block

        try {
            // Check if the token is valid, and extract 'nik' from the token.
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                
                // Look for the projectId(s) that the user has based on their 'nik'.
                List<PenempatanEntity> penempatanEntities = penempatanRepository.findAllByNik(nik);
                // Iterate through each projectId and fetch project details.
                for (PenempatanEntity penempatanEntity : penempatanEntities) {
                    String projectId = penempatanEntity.getProjectId().toString();
                    ProjectEntity project = projectRepository.findByProjectId(projectId);
                    if (project != null){
                        ProjectDetails projectDetail = new ProjectDetails();
                        projectDetail.setProjectId(projectId);
                        projectDetail.setProjectName(project.getNamaProject());
                        projectDetail.setProjectAddress(project.getLokasi());
                        projectDetails.add(projectDetail);
                    }
                }
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
            }

            // Create a response object with project details.
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Project details retrieved successfully");
            response.put("data", projectDetails);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Failed to retrieve project details");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    public ResponseEntity<Map<String, Object>> updateProject(@RequestHeader String tokenWithBearer, @RequestBody ProjectEntity projectId) {
        try {
            String nik = null; // Initialize nik
            
            // Check if the token is valid, and extract 'nik' (assuming it's the user ID) from the token.
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                nik = jwtService.extractUsername(token);
            } else {
                // Handle the case where "Bearer " is not present
                System.out.println("Invalid token format");
            }
    
            if (nik != null) {
                // Create a new PenempatanEntity
                PenempatanEntity penempatanEntity = new PenempatanEntity();
                penempatanEntity.setNik(nik);
                penempatanEntity.setProjectId(projectId);
    
                // obtain the current timestamp for dtmupd from PostgreSQL automatically:
                penempatanEntity.setDtmUpd(null);
    
                // Set the active status
                penempatanEntity.setActive("1");
    
                // Save the entity to the database using the repository
                penempatanEntity = penempatanRepository.save(penempatanEntity);
    
                // Create a response object with a success message
                Map<String, Object> response = new HashMap<>();
                response.put("success: ", true);
                response.put("message: ", "Project updated successfully");
                response.put("data: ", penempatanEntity);
    
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                // Handle the case where nik is null (e.g., token validation failed)
                Map<String, Object> response = new HashMap<>();
                response.put("success: ", false);
                response.put("message: ", "NIK anda bermasalah. silakan kontak administrator");
    
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the process
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update project");
            response.put("error", e.getMessage());
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // ini masih salah, karena ngga berhasil kebaca niknya. padahal udah masuk ke dalem kodingan, tapi secara fungsional masi gagal.
    public ResponseEntity<Map<String, Object>> inputAbsen(@RequestHeader("Authorization") String tokenWithBearer, AbsenRequest request) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
                if (nik != null) {
                    String projectId = request.getProjectId();
                    List<KaryawanEntity> karyawanEntities = karyawanRepository.findNamaByNik(nik);
    
                    if (karyawanEntities.isEmpty()) {
                        // NIK not found in KaryawanEntity
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "NIK not found in KaryawanEntity. Please contact the administrator.");
                        response.put("niknya", nik);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
    
                    String nama = karyawanEntities.get(0).getNama();
    
                    List<ProjectEntity> registeredProjects = penempatanRepository.findProjectIdByNik(nik);
                    boolean projectFound = false;
    
                    for (ProjectEntity penempatanEntity : registeredProjects) {
                        if (penempatanEntity.getProjectId().toString().equals(projectId)) {
                            projectFound = true;
                            break;
                        }
                    }
                    System.out.println(projectFound);
                    if (projectFound) {
                        ProjectEntity projectIdEntity = projectRepository.findByProjectId(projectId);
                        long currentTimeMillis = System.currentTimeMillis();
                        Timestamp dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));
                        if (projectIdEntity != null) {
                            // Save to absen Entity
                            AbsenEntity absenEntity = new AbsenEntity();
                            absenEntity.setProjectId(projectIdEntity);
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
                            absenTrackingEntity.setProjectId(projectIdEntity);
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
                    // save ke absenEntity
                    existingAbsenEntity.setNotePekerjaan(request.getNotePekerjaan());
                    existingAbsenEntity.setGpsLatitudePlg(request.getGpsLatitudePlg());
                    existingAbsenEntity.setGpsLongitudePlg(request.getGpsLatitudePlg());
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
                    List<KaryawanEntity> karyawanEntities = karyawanRepository.findNamaByNik(nik);
                    
                    if (karyawanEntities.isEmpty()) {
                        // NIK not found in KaryawanEntity
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "NIK not found in KaryawanEntity. Please contact the administrator.");
                        response.put("niknya", nik);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    
                    // Extracting the user's name from KaryawanEntity (just in case)
                    String nama = karyawanEntities.get(0).getNama();
                    
                    // Retrieve registered projects for the user
                    List<ProjectEntity> registeredProjects = penempatanRepository.findProjectIdByNik(nik);
                    boolean projectFound = false;
                    
                    // Check if the requested project is among the registered projects
                    for (ProjectEntity penempatanEntity : registeredProjects) {
                        if (penempatanEntity.getProjectId().toString().equals(request.getProjectId())) {
                            projectFound = true;
                            break;
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
}