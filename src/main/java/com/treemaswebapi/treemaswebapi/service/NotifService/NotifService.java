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
                    .liburApproval(null)
                    .lemburApproval(null)
                    .absenPulangApproval(null)
                    .absenWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(cutiAppUploadRepository.findAll())
                    .generalParamApproval(null)
                    .reimburseApproval(null)
                    .dataCounter(cutiAppUploadRepository.count())
                    .build();
                    
                    Long counter = absenAppRepository.countByProjectIdAndIsLibur(projectId, "1");
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil retrieve semua data approval");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getLiburApproval() != null) {
                        data.put("getLiburApproval", approvalResponse.getLiburApproval());
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
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = absenAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApproval() != null) {
                        data.put("cutiApproval", approvalResponse.getCutiApproval());
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
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = absenAppUploadRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getCutiApprovalWeb() != null) {
                        data.put("cutiApprovalWeb", approvalResponse.getCutiApprovalWeb());
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
                    Long counter = absenPulangAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenPulangApproval() != null) {
                        data.put("absenPulangApproval", approvalResponse.getAbsenPulangApproval());
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

    public ResponseEntity<Map<String, Object>> getGeneralParamApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getGeneralParamApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = generalParamAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getGeneralParamApproval() != null) {
                        data.put("generalParamApproval", approvalResponse.getGeneralParamApproval());
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

    public ResponseEntity<Map<String, Object>> getReimburseApproval(String tokenWithBearer, ProjectEntity projectId) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik + "ini udah masuk getReimburseApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = reimburseAppRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getReimburseApproval() != null) {
                        data.put("reimburseApproval", approvalResponse.getReimburseApproval());
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
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = absenAppUploadRepository.count();
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getAbsenWebApproval() != null) {
                        data.put("absenWebApproval", approvalResponse.getAbsenWebApproval());
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
                    System.out.println(nik + "ini udah masuk getAbsenWebApproval");
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .liburApproval(null)
                    .lemburApproval(null)
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
                    Long counter = cutiAppRepository.countByFlgKet("sakit");
                    Map<String, Object> data = new HashMap<>();
                    if (approvalResponse.getSakitApproval() != null) {
                        data.put("sakitApproval", approvalResponse.getSakitApproval());
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
}
