package com.treemaswebapi.treemaswebapi.service.NotifService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.NotifController.request.ApprovalRequest;
import com.treemaswebapi.treemaswebapi.controller.NotifController.response.ApprovalResponse;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenPulangAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.GeneralParamApprovalRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenPulangAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppUploadEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifService {

    private final JwtService jwtService;
    private final AbsenAppRepository absenAppRepository;
    private final CutiAppRepository cutiAppRepository;
    private final CutiAppUploadRepository cutiAppUploadRepository;
    private final AbsenAppUploadRepository absenAppUploadRepository;
    private final AbsenPulangAppRepository absenPulangAppRepository;
    private final GeneralParamApprovalRepository generalParamAppRepository;
    private final ReimburseAppRepository reimburseAppRepository;
    private final ProjectRepository projectRepository;


    // INI BAGIAN GET LIST
    //  public ResponseEntity<Map<String, Object>> getAllApproval(@RequestHeader String tokenWithBearer) {
    //         try {
    //             if (tokenWithBearer.startsWith("Bearer ")) {
    //                 String token = tokenWithBearer.substring("Bearer ".length());
    //                 String nik = jwtService.extractUsername(token);
    //                 System.out.println(nik);
    //                 ApprovalResponse approvalResponse = ApprovalResponse.builder()
    //                 .absenApproval(absenAppRepository.findAll())
    //                 .absenPulangApproval(absenPulangAppRepository.findAll())
    //                 .absenPulangWebApproval(absenAppUploadRepository.findAll())
    //                 .cutiApproval(cutiAppRepository.findAll())
    //                 .cutiApprovalWeb(cutiAppUploadRepository.findAll())
    //                 .generalParamApproval(generalParamAppRepository.findAll())
    //                 .reimburseApproval(reimburseAppRepository.findAll())
    //                 .build();
                    /*
                  

                    
                    
                    
                    */
    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", true);
    //                 response.put("message","berhasil retrieve semua data approval");
    //                 response.put("data", approvalResponse);
    //                 return ResponseEntity.status(HttpStatus.OK).body(response);
    //                 } else {
    //                 // Handle the case where the token format is invalid
    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", false);
    //                 response.put("message", "Invalid token format");
    //                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //             }
    //         } catch (Exception e) {
    //             Map<String, Object> response = new HashMap<>();
    //             response.put("success", false);
    //             response.put("message", "Failed to retrieve project details");
    //             response.put("error", e.getMessage());
    //             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    //         }
    //     }

    // public ResponseEntity<Map<String, Object>> getAbsenApproval(String tokenWithBearer, String projectIdString) {
    //     try {
            
    //         if (tokenWithBearer.startsWith("Bearer ")) {
    //             Optional<ProjectEntity> projectOptional = projectRepository.findById(projectIdString);
    
    //             if (projectOptional.isPresent()) {
    //                 ProjectEntity projectId = projectOptional.get();
    //                 System.out.println(projectIdString);
    //                 String token = tokenWithBearer.substring("Bearer ".length());
    //                 String nik = jwtService.extractUsername(token);
    //                 System.out.println(nik + "ini udah masuk getAbsenApproval");
    //                 List<AbsenAppEntity> absenApproval = absenAppRepository.findAllByProjectId(projectId);
    //                 Long counter = absenAppRepository.countByProjectId(projectId);
    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", true);
    //                 response.put("message", "berhasil retrieve semua data approval");
    //                 response.put("data", absenApproval);
    //                 response.put("dataCount", counter);
    //                 return ResponseEntity.status(HttpStatus.OK).body(response);
    //             } else {
    //                 // Handle the case where the project is not found
    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", false);
    //                 response.put("message", "Project not found with ID: " + projectIdString);
    //                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    //             }
    //         } else {
    //             // Handle the case where the token format is invalid
    //             Map<String, Object> response = new HashMap<>();
    //             response.put("success", false);
    //             response.put("message", "Invalid token format");
    //             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //         }
    //     } catch (Exception e) {
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("success", false);
    //         response.put("message", "Failed to retrieve project details");
    //         response.put("error", e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    //     }
    // }

    public ResponseEntity<Map<String, Object>> getLiburApproval(String tokenWithBearer, String projectIdString) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<ProjectEntity> projectOptional = projectRepository.findById(projectIdString);
    
                if (projectOptional.isPresent()) {
                    ProjectEntity projectId = projectOptional.get();
                    System.out.println(projectIdString);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(absenAppRepository.findAllByProjectIdAndIsLibur(projectId, "1"))
                    .lemburApprovals(null)
                    .absenPulangApprovals(null)
                    .absenWebApprovals(null)
                    .cutiApprovals(null)
                    .cutiApprovalWebs(null)
                    .generalParamApprovals(null)
                    .reimburseApprovals(null)
                    .dataCounter(cutiAppUploadRepository.count())
                    .build();
                    
                    Long counter = absenAppRepository.countByProjectIdAndIsLibur(projectId, "1");
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve semua data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getLiburApprovals() != null) {
                        data.put("getLiburApproval", approvalResponse.getLiburApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Project not found with ID: " + projectIdString);
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

    public ResponseEntity<Map<String, Object>> getLemburApproval(String tokenWithBearer, String projectIdString) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<ProjectEntity> projectOptional = projectRepository.findById(projectIdString);
    
                if (projectOptional.isPresent()) {
                    ProjectEntity projectId = projectOptional.get();
                    System.out.println(projectIdString);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenApproval");
                    List<AbsenAppEntity> lemburApproval = absenAppRepository.findAllByProjectIdAndIsLembur(projectId, "1");
                    Long counter = absenAppRepository.countByProjectIdAndIsLembur(projectId, "1");
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve semua data approval");
                    response.put("data", lemburApproval);
                    response.put("dataCounter", counter);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Project not found with ID: " + projectIdString);
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

    public ResponseEntity<Map<String, Object>> getCutiApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getCutiApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(null)
                    .lemburApprovals(null)
                    .absenPulangApprovals(null)
                    .absenWebApprovals(null)
                    .cutiApprovals(cutiAppRepository.findAll())
                    .cutiApprovalWebs(null)
                    .generalParamApprovals(null)
                    .reimburseApprovals(null)
                    .dataCounter(cutiAppRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = absenAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApprovals() != null) {
                        data.put("cutiApproval", approvalResponse.getCutiApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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

    public ResponseEntity<Map<String, Object>> getCutiWebApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getCutiWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(null)
                    .lemburApprovals(null)
                    .absenPulangApprovals(null)
                    .absenWebApprovals(null)
                    .cutiApprovals(null)
                    .cutiApprovalWebs(cutiAppUploadRepository.findAll())
                    .generalParamApprovals(null)
                    .reimburseApprovals(null)
                    .dataCounter(cutiAppUploadRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = absenAppUploadRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApprovalWebs() != null) {
                        data.put("cutiApprovalWeb", approvalResponse.getCutiApprovalWebs());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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

    public ResponseEntity<Map<String, Object>> getAbsenPulangApproval(String tokenWithBearer, ProjectEntity projectId) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenPulangApproval");
                    ApprovalResponse approvalResponse = new ApprovalResponse();
                    approvalResponse.setAbsenPulangApprovals(absenPulangAppRepository.findAllByProjectId(projectId));
                    approvalResponse.setAbsenWebApprovals(null);
                    approvalResponse.setCutiApprovals(null);
                    approvalResponse.setCutiApprovalWebs(null);
                    approvalResponse.setGeneralParamApprovals(null);
                    approvalResponse.setReimburseApprovals(null);
                    approvalResponse.setDataCounter(absenPulangAppRepository.count());

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = absenPulangAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenPulangApprovals() != null) {
                        data.put("absenPulangApproval", approvalResponse.getAbsenPulangApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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

    // public ResponseEntity<Map<String, Object>> getGeneralParamApproval(String tokenWithBearer) {
    //     try {
    //             if (tokenWithBearer.startsWith("Bearer ")) {
    //                 String token = tokenWithBearer.substring("Bearer ".length());
    //                 String nik = jwtService.extractUsername(token);
    //                 System.out.println(nik + "ini udah masuk getGeneralParamApproval");
    //                 ApprovalResponse approvalResponse = ApprovalResponse.builder()
    //                 .liburApprovals(null)
    //                 .lemburApprovals(null)
    //                 .absenPulangApprovals(null)
    //                 .absenWebApprovals(null)
    //                 .cutiApprovals(null)
    //                 .cutiApprovalWebs(null)
    //                 .generalParamApprovals(generalParamAppRepository.findAll())
    //                 .reimburseApprovals(null)
    //                 .dataCounter(generalParamAppRepository.count())
    //                 .build();

    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", true);
    //                 response.put("message","berhasil retrieve semua data approval");
    //                 Long counter = generalParamAppRepository.count();
    //                 Map<String, Object> data = new HashMap<>();
    //                 if (approvalResponse.getGeneralParamApproval() != null) {
    //                     data.put("generalParamApproval", approvalResponse.getGeneralParamApproval());
    //                 }
    //                 response.put("data", data);
    //                 response.put("dataCounter", counter);
    //                 return ResponseEntity.status(HttpStatus.OK).body(response);
    //                 } else {
    //                 // Handle the case where the token format is invalid
    //                 Map<String, Object> response = new HashMap<>();
    //                 response.put("success", false);
    //                 response.put("message", "Invalid token format");
    //                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //             }
    //         } catch (Exception e) {
    //             Map<String, Object> response = new HashMap<>();
    //             response.put("success", false);
    //             response.put("message", "Failed to retrieve project details");
    //             response.put("error", e.getMessage());
    //             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    //         }
    // }

    public ResponseEntity<Map<String, Object>> getReimburseApproval(String tokenWithBearer, ProjectEntity projectId) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getReimburseApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(null)
                    .lemburApprovals(null)
                    .absenPulangApprovals(null)
                    .absenWebApprovals(null)
                    .cutiApprovals(null)
                    .cutiApprovalWebs(null)
                    .generalParamApprovals(null)
                    .reimburseApprovals(reimburseAppRepository.findAllByProjectId(projectId))
                    .dataCounter(reimburseAppRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = reimburseAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getReimburseApprovals() != null) {
                        data.put("reimburseApproval", approvalResponse.getReimburseApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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
    public ResponseEntity<Map<String, Object>> getAbsenWebApproval(String tokenWithBearer, ProjectEntity projectId) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(null)
                    .lemburApprovals(null)
                    .absenPulangApprovals(null)
                    .absenWebApprovals(absenAppUploadRepository.findAllByProjectId(projectId))
                    .cutiApprovals(null)
                    .cutiApprovalWebs(null)
                    .generalParamApprovals(null)
                    .reimburseApprovals(null)
                    .dataCounter(absenAppUploadRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = absenAppUploadRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenWebApprovals() != null) {
                        data.put("absenWebApproval", approvalResponse.getAbsenWebApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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
    public ResponseEntity<Map<String, Object>> getSakitApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getSakitApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApprovals(null)
                    .lemburApprovals(null)
                    .sakitApprovals(cutiAppRepository.findByFlgKet("sakit"))
                    .absenPulangApprovals(null)
                    .absenWebApprovals(null)
                    .cutiApprovals(null)
                    .cutiApprovalWebs(null)
                    .generalParamApprovals(null)
                    .reimburseApprovals(null)
                    .dataCounter(cutiAppRepository.countByFlgKet("sakit"))
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    Long counter = cutiAppRepository.countByFlgKet("sakit");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getSakitApprovals() != null) {
                        data.put("sakitApproval", approvalResponse.getSakitApprovals());
                    }
                    response.put("data", data);
                    response.put("dataCounter", counter);
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
    public ResponseEntity<Map<String, Object>> getFullCounter(String tokenWithBearer){
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    Long absenApprovalCount = absenAppRepository.count();
                    Long absenPulangApprovalCount = absenPulangAppRepository.count();
                    Long absenWebApprovalCount = absenAppUploadRepository.count();
                    Long cutiApprovalCount = cutiAppRepository.countByFlgKet("cuti");
                    Long cutiApprovalWebCount = cutiAppUploadRepository.countByFlgKet("cuti");
                    Long sakitApprovalCount = cutiAppRepository.countByFlgKet("sakit");
                    Long generalParamApprovalCount = generalParamAppRepository.count();
                    Long reimburseApprovalCount = reimburseAppRepository.count();
                    Long fullCounter = absenApprovalCount + absenPulangApprovalCount + cutiApprovalCount + 
                    cutiApprovalWebCount + sakitApprovalCount + 
                    generalParamApprovalCount + reimburseApprovalCount + absenWebApprovalCount;
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("berhasil menghitung semua data approval", false);
                    response.put("dataCount", fullCounter);
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

    // INI BAGIIAN POST SERVICE
    public ResponseEntity<Map<String, Object>> postLiburApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk postLiburApproval");

                    Optional<AbsenAppEntity> datanya = absenAppRepository.findById(idApproval);

                    if (request.getIsApprove() == "1") {
                        datanya.get().setIsApprove("1");
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("berhasil menghitung semua data approval", false);
                    response.put("dataCount", nik);
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

    public ResponseEntity<Map<String, Object>> postLemburApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postCutiApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postCutiWebApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postAbsenPulangApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postAbsenWebApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postGeneralParamApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postReimburseApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postSakitApproval(String tokenWithBearer, Long idApproval) {
        return null;
    }


    // GET DETAIL
    public ResponseEntity<Map<String, Object>> getDetailLiburApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<AbsenAppEntity> dataApprovalnya = absenAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getDetailLiburApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getLiburApproval() != null) {
                        data.put("getLiburApproval", approvalResponse.getLiburApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data libur not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailLemburApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<AbsenAppEntity> dataApprovalnya = absenAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getDetailLemburApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .lemburApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getLemburApproval() != null) {
                        data.put("getLemburApproval", approvalResponse.getLemburApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data lembur not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailCutiApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<CutiAppEntity> dataApprovalnya = cutiAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getDetailCutiApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .cutiApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApproval() != null) {
                        data.put("getCutiApproval", approvalResponse.getCutiApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Cuti not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailCutiWebApproval(String tokenWithBearer, Long idApproval) {
       try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<CutiAppUploadEntity> dataApprovalnya = cutiAppUploadRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getDetailCutiWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .cutiApprovalWeb(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApprovalWeb() != null) {
                        data.put("getDetailCutiWebApproval", approvalResponse.getCutiApprovalWeb());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Cuti Web not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailAbsenPulangApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<AbsenPulangAppEntity> dataApprovalnya = absenPulangAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getDetailAbsenPulangApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenPulangApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenPulangApproval() != null) {
                        data.put("getAbsenPulangApproval", approvalResponse.getAbsenPulangApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Absen Pulang not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailAbsenWebApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<AbsenAppUploadEntity> dataApprovalnya = absenAppUploadRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenWebApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenWebApproval() != null) {
                        data.put("getDetailAbsenWebApproval", approvalResponse.getAbsenWebApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Absen Web not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // public ResponseEntity<Map<String, Object>> getDetailGeneralParamApproval(String tokenWithBearer, Long idApproval) {
    //     return null;
    // }

    public ResponseEntity<Map<String, Object>> getDetailReimburseApproval(String tokenWithBearer, Long idApproval) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<ReimburseAppEntity> dataApprovalnya = reimburseAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk reimburseApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .reimburseApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getReimburseApproval() != null) {
                        data.put("getDetailReimburseApproval", approvalResponse.getReimburseApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Reimburse not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDetailSakitApproval(String tokenWithBearer, Long idApproval) {
       try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<CutiAppEntity> dataApprovalnya = cutiAppRepository.findById(idApproval);
    
                if (dataApprovalnya.isPresent()) {
                    System.out.println(dataApprovalnya);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk reimburseApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .sakitApproval(dataApprovalnya)
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getSakitApproval() != null) {
                        data.put("getSakitApproval", approvalResponse.getSakitApproval());
                    }
                    response.put("data", data);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Handle the case where the project is not found
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Data Sakit not found with ID: " + idApproval);
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
            response.put("message", "KODINGANNYA EROR ATAU REQUEST LO GA MASUK");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

}
