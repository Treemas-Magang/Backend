package com.treemaswebapi.treemaswebapi.service.AbsenService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.AbsenResponse;
import com.treemaswebapi.treemaswebapi.controller.MemberController.request.MemberRequest;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectDetails;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenImgRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.PenempatanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PenempatanRepository penempatanRepository;
    private final AbsenImgRepository absenImgRepository;
    private final AbsenRepository absenRepository;
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;


    // fungsi untuk narik data project mana aja yang dipegang si leader
    // fungsi ini difilter bukan dari backend, tapi dari front-end
    public ResponseEntity<Map<String, Object>> leaderProjectDetails(@RequestHeader String tokenWithBearer, @RequestParam("projectId") String projectIdReq) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring("Bearer ".length());
                String nik = jwtService.extractUsername(token);
    
                // Retrieve project IDs associated with the 'nik'
                List<ProjectDetails> projectIds = retrieveProjectDetailsForNik(nik);
    
                if (!projectIds.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Project IDs retrieved successfully");
                    response.put("data", projectIds);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No project IDs found for the 'nik'");
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
            response.put("message", "Failed to retrieve project IDs");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private List<ProjectDetails> retrieveProjectDetailsForNik(String nik) {
    try {
        List<PenempatanEntity> penempatanEntities = penempatanRepository.findByNik(nik);

        List<ProjectDetails> projectDetails = new ArrayList<>();

        for (PenempatanEntity penempatanEntity : penempatanEntities) {
            String projectId = penempatanEntity.getProjectId().toString();
            ProjectEntity project = projectRepository.findByProjectId(projectId);

            if (project != null) {
                ProjectDetails details = new ProjectDetails();
                details.setProjectId(project.getProjectId().toString());
                details.setProjectName(project.getNamaProject());
                projectDetails.add(details);
            }
        }

        return projectDetails;
    } catch (Exception e) {
        return new ArrayList<>();
    }
}



    // fungsi untuk narik data absen secara keseluruhan dengan projectId yang udah dipilih dari front-end
    public ResponseEntity<Map<String, Object>> getAbsenFromProjectId(@RequestHeader("Authorization") String tokenWithBearer, @RequestBody MemberRequest request) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                List<AbsenEntity> absenEntities = absenRepository.findAllByProjectIdAndTglAbsen(request.getProjectId(), request.getTargetDate());

                if (!absenEntities.isEmpty()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Absen data retrieved successfully");
                    response.put("data", absenEntities);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "No Absen data found for the provided projectId");

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } else {
                // Handle the case where the token is not valid
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid token format");
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve Absen data");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // this function serve as a method to get all the projects that we have. and it could be accessed by everyone in the company
        public ResponseEntity<Map<String, Object>> projectDetails(@RequestHeader String tokenWithBearer) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println("kesini " + nik);
        
                    List<ProjectEntity> allProjects = projectRepository.findAll();
                    List<ProjectDetails> projectDetailsList = new ArrayList<>();
        
                    for (ProjectEntity project : allProjects) {
                        if (project != null) {
                            ProjectDetails details = new ProjectDetails();
                            PenempatanEntity penempatan = penempatanRepository.findActiveByProjectIdAndNik(project, nik);
                            System.out.println("INI PENEMPATANNYA: "+ penempatan);
                            details.setProjectId(project.getProjectId().toString());
                            details.setProjectName(project.getNamaProject());
                            details.setProjectAddress(project.getLokasi());
                            details.setJrkMax(project.getJrkMax());
                            details.setGpsLongitude(project.getGpsLongitude());
                            details.setGpsLatitude(project.getGpsLatitude());
                            details.setJamMasuk(project.getJamMasuk());
                            details.setJamKeluar(project.getJamKeluar());
                            if (penempatan != null) {
                                details.setActive(penempatan.getActive());
                            } else {
                                details.setActive(null); // or any default value you want to set
                            }                    
                            projectDetailsList.add(details);
                        }
                    }
        
                    if (!projectDetailsList.isEmpty()) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("message", "Project details retrieved successfully");
                        response.put("data", projectDetailsList);
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "No project details found for the 'nik'");
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
                response.put("message", "Failed to retrieve project details");
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        public ResponseEntity<Map<String, Object>> getAbsenMemberByProjectId(@RequestHeader("Authorization") String tokenWithBearer, @RequestParam("projectId") ProjectEntity projectId) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    LocalDate date = LocalDate.now();
                    //mau narik data yang ada di penempatanEntity, cari by projectId
                    List<AbsenEntity> listAbsen = absenRepository.findAllByProjectIdAndTglAbsen(projectId, date);
                    Map<String, Object> response = new HashMap<>();
                    if (listAbsen.isEmpty()) {
                        response.put("success", true);
                        response.put("message", "belum ada member yang absen");
                        response.put("data", listAbsen);
                    }else{
                    response.put("success", true);
                    response.put("message", "berhasil mendapatkan member by projectId");
                    response.put("data", listAbsen);
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(response);
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
                response.put("message", "Failed to retrieve project details");
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        
        public ResponseEntity<Map<String, Object>> getDataAbsenMember(@RequestHeader("Authorization") String tokenWithBearer, @RequestParam("idAbsen") Long idAbsen) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    //mau narik data yang ada di penempatanEntity, cari by projectId
                    AbsenEntity dataAbsen = absenRepository.findByIdAbsen(idAbsen);
                    System.out.println("ini data absennya"+dataAbsen);
                    String dataAbsenImg = absenImgRepository.findById(idAbsen).get().getImage64();
                    System.out.println("ini data gambarnya"+dataAbsenImg);

                    if(dataAbsenImg == null){
                    AbsenResponse absenResponse = new AbsenResponse();
                    absenResponse.setAbsenEntity(dataAbsen);
                    absenResponse.setAbsenImg(null);
                    }
                    AbsenResponse absenResponse = new AbsenResponse();
                    if (dataAbsenImg == null) {           
                    absenResponse.setAbsenEntity(dataAbsen);
                    absenResponse.setAbsenImg(null);
                    }else{
                    absenResponse.setAbsenEntity(dataAbsen);
                    absenResponse.setAbsenImg(dataAbsenImg);
                    }
                    Map<String, Object> response = new HashMap<>();
                    if (dataAbsen == null) {
                        response.put("success", false);
                        response.put("message", "idAbsen salah");
                        response.put("data", absenResponse);
                        //ini penambahan fungsinya
                    }else{
                    response.put("success", true);
                    response.put("message", "berhasil mendapatkan data absen seorang member");
                    response.put("data", absenResponse);
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(response);
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
                response.put("message", "Failed to retrieve project details");
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        public ResponseEntity<Map<String, Object>> getProject(String tokenWithBearer) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
        
                    List<PenempatanEntity> projectPenempatan = penempatanRepository.findByNik(nik);
        
                    if (!projectPenempatan.isEmpty()) {
                        Map<String, Object> response = new HashMap<>();
                        List<ProjectDetails> projectDetailsList = new ArrayList<>();
        
                        for (PenempatanEntity penempatanEntity : projectPenempatan) {
                            ProjectEntity project = penempatanEntity.getProjectId();
        
                            if (project != null) {
                                List<PenempatanEntity> members = penempatanRepository.findAllByProjectId(project);
        
                                if (!members.isEmpty()) {
                                    ProjectDetails details = new ProjectDetails();
                                    PenempatanEntity penempatan = penempatanRepository.findActiveByProjectIdAndNik(project, nik);
        
                                    details.setProjectId(project.getProjectId().toString());
                                    details.setProjectName(project.getNamaProject());
                                    details.setProjectAddress(project.getLokasi());
                                    details.setJrkMax(project.getJrkMax());
                                    details.setGpsLongitude(project.getGpsLongitude());
                                    details.setGpsLatitude(project.getGpsLatitude());
                                    details.setJamMasuk(project.getJamMasuk());
                                    details.setJamKeluar(project.getJamKeluar());
        
                                    if (penempatan != null) {
                                        details.setActive(penempatan.getActive());
                                    } else {
                                        details.setActive(null); // or any default value you want to set
                                    }
        
                                    projectDetailsList.add(details);
                                }
                            }
                        }
        
                        if (!projectDetailsList.isEmpty()) {
                            response.put("success", true);
                            response.put("message", "Project details retrieved successfully");
                            response.put("data", projectDetailsList);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } else {
                            response.put("success", false);
                            response.put("message", "No member data found!");
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                        }
                    } else {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "No project placement found for the user!");
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
                response.put("message", "Failed to retrieve project details");
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        // public ResponseEntity<Map<String, Object>> getAllAbsen(@RequestHeader("Authorization") String tokenWithBearer, @RequestBody MemberRequest request) {
        //         try {
        //             if (tokenWithBearer.startsWith("Bearer ")) {
        //                 List<AbsenEntity> absenEntities = absenRepository.findAll();
        //                 AbsenResponse absenResponse = 
        //             } else {
        //                 // Handle the case where the token is not valid
        //                 Map<String, Object> response = new HashMap<>();
        //                 response.put("success", false);
        //                 response.put("message", "Invalid token format");
                        
        //                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        //             }
        //         } catch (Exception e) {
        //             Map<String, Object> response = new HashMap<>();
        //             response.put("success", false);
        //             response.put("message", "Failed to retrieve Absen data");
        //             response.put("error", e.getMessage());

        //             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        //         }
        //     }
}
