package com.treemaswebapi.treemaswebapi.service.NotifService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.NotifController.response.ApprovalResponse;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenPulangAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.GeneralParamApprovalRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
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

    public ResponseEntity<Map<String, Object>> getAbsenApproval(String tokenWithBearer, String projectIdString) {
        try {
            if (tokenWithBearer.startsWith("Bearer ")) {
                Optional<ProjectEntity> projectOptional = projectRepository.findById(projectIdString);
    
                if (projectOptional.isPresent()) {
                    ProjectEntity projectId = projectOptional.get();
                    System.out.println(projectIdString);
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getAbsenApproval");
                    List<AbsenAppEntity> absenApproval = absenAppRepository.findAllByProjectId(projectId);
    
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve semua data approval");
                    response.put("data", absenApproval);
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
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(cutiAppRepository.findAll())
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
                    .dataCounter(cutiAppRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(cutiAppUploadRepository.findAll())
                    .generalParamApproval(null)
                    .reimburseApproval(null)
                    .dataCounter(cutiAppUploadRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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
                    approvalResponse.setAbsenApproval(null);
                    approvalResponse.setAbsenPulangApproval(absenPulangAppRepository.findAllByProjectId(projectId));
                    approvalResponse.setAbsenWebApproval(null);
                    approvalResponse.setCutiApproval(null);
                    approvalResponse.setCutiApprovalWeb(null);
                    approvalResponse.setGeneralParamApproval(null);
                    approvalResponse.setReimburseApproval(null);
                    approvalResponse.setDataCounter(absenPulangAppRepository.count());

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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

    public ResponseEntity<Map<String, Object>> getGeneralParamApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getGeneralParamApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(generalParamAppRepository.findAll())
                    .reimburseApproval(null)
                    .dataCounter(generalParamAppRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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

    public ResponseEntity<Map<String, Object>> getReimburseApproval(String tokenWithBearer, ProjectEntity projectId) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getReimburseApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(reimburseAppRepository.findAllByProjectId(projectId))
                    .dataCounter(reimburseAppRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(absenAppUploadRepository.findAllByProjectId(projectId))
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
                    .dataCounter(absenAppUploadRepository.count())
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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
                    System.out.println(nik + "ini udah masuk getAbsenWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .sakitApproval(cutiAppRepository.findByFlgKet("sakit"))
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
                    .dataCounter(cutiAppRepository.countByFlgKet("sakit"))
                    .build();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message","berhasil retrieve semua data approval");
                    response.put("data", approvalResponse);
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
}
