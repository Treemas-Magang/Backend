package com.treemaswebapi.treemaswebapi.service.NotifService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.NotifController.request.ApprovalRequest;
import com.treemaswebapi.treemaswebapi.controller.NotifController.response.ApprovalResponse;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenPulangAppRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiRepository;
import com.treemaswebapi.treemaswebapi.repository.GeneralParamApprovalRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenPulangAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;
import com.treemaswebapi.treemaswebapi.entity.JabatanEntity.JabatanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifService {

    private final JwtService jwtService;
    private final AbsenAppRepository absenAppRepository;
    private final AbsenRepository absenRepository;
    private final CutiAppRepository cutiAppRepository;
    private final CutiRepository cutiRepository;
    private final CutiAppUploadRepository cutiAppUploadRepository;
    private final AbsenAppUploadRepository absenAppUploadRepository;
    private final AbsenPulangAppRepository absenPulangAppRepository;
    private final GeneralParamApprovalRepository generalParamAppRepository;
    private final ReimburseAppRepository reimburseAppRepository;
    private final ProjectRepository projectRepository;
    private final KaryawanRepository karyawanRepository;
    private final SysUserRepository sysUserRepository;

    // INI BAGIAN GET LIST
      public ResponseEntity<Map<String, Object>> getAllApproval(@RequestHeader String tokenWithBearer) {
             try {
                 if (tokenWithBearer.startsWith("Bearer ")) {
                     String token = tokenWithBearer.substring("Bearer ".length());
                     String nik = jwtService.extractUsername(token);
                     System.out.println(nik);
                     ApprovalResponse approvalResponse = ApprovalResponse.builder()
                     .liburApprovals(absenAppRepository.findByIsApproveIsNull())
                     .absenPulangApprovals(absenPulangAppRepository.findByIsApproveIsNull())
                     .absenWebApprovals(absenAppUploadRepository.findByIsApproveIsNull())
                     .cutiApprovals(cutiAppRepository.findByIsApprovedIsNull())
                     .cutiApprovalWebs(cutiAppUploadRepository.findByIsApprovedIsNull())
                     .reimburseApprovals(reimburseAppRepository.findByIsApproveIsNull())
                     .build();
                    /*
                  

                    
                    
                    
                    */
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
                    .liburApprovals(absenAppRepository.findAllByProjectIdAndIsLiburAndIsApproveIsNull(projectId, "1"))
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
                    List<AbsenAppEntity> lemburApproval = absenAppRepository.findAllByProjectIdAndIsLemburAndIsApproveIsNull(projectId, "1");
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
                    .cutiApprovals(cutiAppRepository.findByIsApprovedIsNull())
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
                    .cutiApprovalWebs(cutiAppUploadRepository.findByIsApprovedIsNull())
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
                    approvalResponse.setAbsenPulangApprovals(absenPulangAppRepository.findAllByProjectIdAndIsApproveIsNull(projectId));
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
                    .reimburseApprovals(reimburseAppRepository.findAllByProjectIdAndIsApproveIsNull(projectId))
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
                    Long absenApprovalCount = absenAppRepository.countByIsApproveIsNull();
                    Long absenPulangApprovalCount = absenPulangAppRepository.countByIsApproveIsNull();
                    Long absenWebApprovalCount = absenAppUploadRepository.countByIsApproveIsNull();
                    Long cutiApprovalCount = cutiAppRepository.countByFlgKetAndIsApprovedIsNull("cuti");
                    Long cutiApprovalWebCount = cutiAppUploadRepository.countByFlgKetAndIsApprovedIsNull("cuti");
                    Long sakitApprovalCount = cutiAppRepository.countByFlgKetAndIsApprovedIsNull("sakit");
                    Long generalParamApprovalCount = generalParamAppRepository.countByIsApproveIsNull();
                    Long reimburseApprovalCount = reimburseAppRepository.countByIsApproveIsNull();
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
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLiburApproval");

                    Optional<AbsenAppEntity> datanyaOptional = absenAppRepository.findById(idApproval);
                    if (datanyaOptional.isPresent()) {
                        AbsenAppEntity datanya = datanyaOptional.get();
                        AbsenEntity dataAbsen = new AbsenEntity();
                        if ("1".equals(request.getIsApprove())) {
                            datanya.setIsApprove("1");
                            datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                            datanya.setUsrApp(namaUser);
                            datanya.setNoteApp(request.getNoteApp());
                            absenAppRepository.save(datanya);

                            dataAbsen = AbsenEntity.builder()
                            .dtmApp(Timestamp.valueOf(LocalDateTime.now()))
                            .dtmCrt(datanya.getDtmCrt())
                            .gpsLatitudeMsk(datanya.getGpsLatitudeMsk())
                            .gpsLatitudePlg(datanya.getGpsLatitudePlg())
                            .gpsLongitudeMsk(datanya.getGpsLongitudeMsk())
                            .gpsLongitudePlg(datanya.getGpsLongitudePlg())
                            .hari(datanya.getHari())
                            .isAbsen("1")
                            .isCuti("0")
                            .isLembur(datanya.getIsLembur())
                            .isLibur(datanya.getIsLibur())
                            .isOther(datanya.getIsOther())
                            .isSakit(datanya.getIsSakit())
                            .isWfh(datanya.getIsWfh())
                            .jamMsk(datanya.getJamMsk())
                            .jamPlg(datanya.getJamPlg())
                            .jarakMsk(datanya.getJarakMsk())
                            .jarakPlg(datanya.getJarakPlg())
                            .lokasiMsk(datanya.getLokasiMsk())
                            .lokasiPlg(datanya.getLokasiPlg())
                            .nama(datanya.getNama())
                            .nik(datanya.getNik())
                            .projectId(datanya.getProjectId())
                            .tglAbsen(datanya.getTglAbsen())
                            .totalJamKerja(datanya.getTotalJamKerja())
                            .usrApp(namaUser)
                            .usrCrt(datanya.getUsrCrt())
                            .build();
                            absenRepository.save(dataAbsen);

                            absenAppRepository.delete(datanya);
                        }else if ("0".equals(request.getIsApprove())) {
                            datanya.setIsApprove("0");
                            datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                            datanya.setUsrApp(namaUser);
                            datanya.setNoteApp(request.getNoteApp());
                            absenAppRepository.save(datanya);
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", true);
                            response.put("message", "berhasil post ke AbsenEntity dan SetValue di AbsenApp");
                            response.put("data", datanya);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        }
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("message", "berhasil post ke AbsenEntity dan SetValue di AbsenApp");
                        response.put("data", datanya);
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    }else{
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", false);
                        response.put("message", "dataOptionalnya ngga ada");
                        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    public ResponseEntity<Map<String, Object>> postLemburApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLemburApproval");

                    AbsenAppEntity datanya = absenAppRepository.findById(idApproval).get();
                    AbsenEntity dataAbsen = new AbsenEntity();
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setIsApprove("1");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        absenAppRepository.save(datanya);

                        dataAbsen = AbsenEntity.builder()
                        .dtmApp(Timestamp.valueOf(LocalDateTime.now()))
                        .dtmCrt(datanya.getDtmCrt())
                        .gpsLatitudeMsk(datanya.getGpsLatitudeMsk())
                        .gpsLatitudePlg(datanya.getGpsLatitudePlg())
                        .gpsLongitudeMsk(datanya.getGpsLongitudeMsk())
                        .gpsLongitudePlg(datanya.getGpsLongitudePlg())
                        .hari(datanya.getHari())
                        .isAbsen("1")
                        .isCuti("0")
                        .isLembur(datanya.getIsLembur())
                        .isLibur(datanya.getIsLibur())
                        .isOther(datanya.getIsOther())
                        .isSakit(datanya.getIsSakit())
                        .isWfh(datanya.getIsWfh())
                        .jamMsk(datanya.getJamMsk())
                        .jamPlg(datanya.getJamPlg())
                        .jarakMsk(datanya.getJarakMsk())
                        .jarakPlg(datanya.getJarakPlg())
                        .lokasiMsk(datanya.getLokasiMsk())
                        .lokasiPlg(datanya.getLokasiPlg())
                        .nama(datanya.getNama())
                        .nik(datanya.getNik())
                        .projectId(datanya.getProjectId())
                        .tglAbsen(datanya.getTglAbsen())
                        .totalJamKerja(datanya.getTotalJamKerja())
                        .usrApp(namaUser)
                        .usrCrt(datanya.getUsrCrt())
                        .build();
                        absenRepository.save(dataAbsen);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApprove("0");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        absenAppRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke AbsenEntity dan SetValue di AbsenApp");
                    response.put("data", datanya);
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

    public ResponseEntity<Map<String, Object>> postCutiApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
         try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLemburApproval");

                    CutiAppEntity datanya = cutiAppRepository.findById(idApproval).get();
                    CutiEntity dataCuti = new CutiEntity();
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setIsApproved("1");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppRepository.save(datanya);

                        dataCuti = CutiEntity.builder()
                        .alamatCuti(datanya.getAlamatCuti())
                        .dtmApp(datanya.getDtmApp())
                        .dtmCrt(datanya.getDtmCrt())
                        .flagApp("-")
                        .flgKet(datanya.getFlgKet())
                        .isApproved("1")
                        .jmlCuti(datanya.getJmlCuti())
                        .jmlCutiBersama(datanya.getJmlCutiBersama())
                        .jmlCutiKhusus(datanya.getJmlCutiKhusus())
                        .keperluanCuti(datanya.getKeperluanCuti())
                        .nama(datanya.getNama())
                        .nik(datanya.getNik())
                        .sisaCuti(datanya.getSisaCuti())
                        .tglKembaliKerja(datanya.getTglKembaliKerja())
                        .tglMulai(datanya.getTglMulai())
                        .tglSelesai(datanya.getTglSelesai())
                        .build();
                        cutiRepository.save(dataCuti);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApproved("0");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke CutiEntity dan SetValue di CutiApp");
                    response.put("data", datanya);
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

    public ResponseEntity<Map<String, Object>> postCutiWebApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLemburApproval");

                    CutiAppUploadEntity datanya = cutiAppUploadRepository.findById(idApproval).get();
                    CutiEntity dataCuti = new CutiEntity();
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setIsApproved("1");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppUploadRepository.save(datanya);

                        dataCuti = CutiEntity.builder()
                        .alamatCuti(datanya.getAlamatCuti())
                        .dtmApp(datanya.getDtmApp())
                        .dtmCrt(datanya.getDtmCrt())
                        .flagApp("-")
                        .flgKet(datanya.getFlgKet())
                        .isApproved("1")
                        .jmlCutiBersama(datanya.getJmlCutiBersama())
                        .jmlCutiKhusus(datanya.getJmlCutiKhusus())
                        .keperluanCuti(datanya.getKeperluanCuti())
                        .nama(datanya.getNama())
                        .nik(datanya.getNik())
                        .tglKembaliKerja(datanya.getTglKembaliKerja())
                        .tglMulai(datanya.getTglMulai())
                        .tglSelesai(datanya.getTglSelesai())
                        .build();
                        cutiRepository.save(dataCuti);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApproved("0");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppUploadRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke CutiEntity dan SetValue di CutiAppUpload");
                    response.put("data", datanya);
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

    public ResponseEntity<Map<String, Object>> postAbsenPulangApproval1(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
            String jabatanWajib = "LEAD";
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLemburApproval");
                    if(!dataUser.isPresent()){
                        throw new RuntimeException("User not found");
                    }
                    String jabatan = sysUserRepository.findByUserId(nikUser)
                                    .map(SysUserEntity::getRole)
                                    .map(JabatanEntity::getJabatanId)
                                    .orElse(null);

                    if (!jabatanWajib.equals(jabatan)) {
                        throw new SecurityException("Unauthorized access - User does not have the required role");
                    }

                    AbsenPulangAppEntity datanya = absenPulangAppRepository.findById(idApproval).get();
                    AbsenEntity dataAbsen = absenRepository.findByIdAbsen(idApproval);
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setFlagApp("1");
                        datanya.setDtmApp1(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp1(namaUser);
                        datanya.setNoteApp1(request.getNoteApp());
                        absenPulangAppRepository.save(datanya);

                        // dataAbsen.setDtmApp(null);
                        // dataAbsen.setDtmCrt(null);
                        // dataAbsen.setGpsLatitudeMsk(null);
                        // dataAbsen.setGpsLatitudePlg(null);
                        // dataAbsen.setGpsLongitudeMsk(null);
                        // dataAbsen.setGpsLongitudePlg(null);
                        // dataAbsen.setHari(namaUser);
                        // dataAbsen.setIsAbsen("1");
                        // dataAbsen.setIsCuti(namaUser);
                        // dataAbsen.setIsLembur(namaUser);
                        // dataAbsen.setIsLibur(namaUser);
                        // dataAbsen.setIsOther(namaUser);
                        // dataAbsen.setIsSakit(namaUser);
                        // dataAbsen.setIsWfh(namaUser);
                        // dataAbsen.setJamMsk(null);
                        // dataAbsen.setJamPlg(null);
                        // dataAbsen.setJarakMsk(namaUser);
                        // dataAbsen.setJarakPlg(namaUser);
                        // dataAbsen.setLokasiMsk(namaUser);
                        // dataAbsen.setLokasiPlg(namaUser);
                        // dataAbsen.setNama(namaUser);
                        // dataAbsen.setNik(nikUser);
                        // dataAbsen.setNoteApp(namaUser);
                        // dataAbsen.setNoteOther(namaUser);
                        // dataAbsen.setNotePekerjaan(namaUser);
                        // dataAbsen.setNotePlgCepat(namaUser);
                        // dataAbsen.setNoteTelatMsk(namaUser);
                        // dataAbsen.setProjectId(null);
                        // dataAbsen.setTglAbsen(null);
                        // dataAbsen.setTotalJamKerja(null);
                        // dataAbsen.setUsrApp(namaUser);
                        // dataAbsen.setUsrCrt(namaUser);

                        // absenRepository.save(dataAbsen);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApprove("0");
                        datanya.setDtmApp1(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp1(namaUser);
                        datanya.setNoteApp1(request.getNoteApp());
                        absenPulangAppRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke CutiEntity dan SetValue di CutiAppUpload");
                    response.put("data", datanya);
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

    public ResponseEntity<Map<String, Object>> postAbsenPulangApproval2(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
            String jabatanWajib = "HEAD";
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postLemburApproval");
                    if(!dataUser.isPresent()){
                        throw new RuntimeException("User not found");
                    }
                    String jabatan = sysUserRepository.findByUserId(nikUser)
                                    .map(SysUserEntity::getRole)
                                    .map(JabatanEntity::getJabatanId)
                                    .orElse(null);

                    if (!jabatanWajib.equals(jabatan)) {
                        throw new SecurityException("Unauthorized access - User does not have the required role");
                    }

                    AbsenPulangAppEntity datanya = absenPulangAppRepository.findById(idApproval).get();
                    AbsenEntity dataAbsen = absenRepository.findByIdAbsen(idApproval);
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setIsApprove("1");
                        datanya.setDtmApp2(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp2(namaUser);
                        datanya.setNoteApp2(request.getNoteApp());
                        absenPulangAppRepository.save(datanya);

                        //yang dikomen di bawah ini, ngga perlu diset dari sini karena data masuknya udah ada di dataAbsen
                        dataAbsen.setDtmApp(datanya.getDtmApp2());
                        dataAbsen.setDtmCrt(datanya.getDtmUpd());
                        // dataAbsen.setGpsLatitudeMsk(datanya.get);
                        dataAbsen.setGpsLatitudePlg(datanya.getGpsLatitudePlg());
                        // dataAbsen.setGpsLongitudeMsk(null);
                        dataAbsen.setGpsLongitudePlg(datanya.getGpsLongitudePlg());
                        // dataAbsen.setHari(namaUser);
                        // dataAbsen.setIsAbsen("1");
                        // dataAbsen.setIsCuti(namaUser);
                        // dataAbsen.setIsLembur(namaUser);
                        // dataAbsen.setIsLibur(namaUser);
                        // dataAbsen.setIsOther(namaUser);
                        // dataAbsen.setIsSakit(namaUser);
                        // dataAbsen.setIsWfh(namaUser);
                        // dataAbsen.setJamMsk(null);
                        dataAbsen.setJamPlg(datanya.getJamPlg());
                        // dataAbsen.setJarakMsk(namaUser);
                        dataAbsen.setJarakPlg(datanya.getJarakPlg());
                        // dataAbsen.setLokasiMsk(namaUser);
                        dataAbsen.setLokasiPlg(datanya.getLokasiPlg());
                        // dataAbsen.setNama(namaUser);
                        // dataAbsen.setNik(nikUser);
                        dataAbsen.setNoteApp(datanya.getNoteApp2());
                        // dataAbsen.setNoteOther(namaUser);
                        dataAbsen.setNotePekerjaan(datanya.getNotePekerjaan());
                        dataAbsen.setNotePlgCepat(datanya.getNotePlgCepat());
                        // dataAbsen.setNoteTelatMsk(namaUser);
                        // dataAbsen.setProjectId(null);
                        // dataAbsen.setTglAbsen(null);
                        dataAbsen.setTotalJamKerja(datanya.getTotalJamKerja());
                        dataAbsen.setUsrApp(datanya.getUsrApp2());
                        dataAbsen.setUsrCrt(datanya.getUsrUpd());

                        absenRepository.save(dataAbsen);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApprove("0");
                        datanya.setDtmApp1(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp1(namaUser);
                        datanya.setNoteApp1(request.getNoteApp());
                        absenPulangAppRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke CutiEntity dan SetValue di CutiAppUpload");
                    response.put("data", datanya);
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

    public ResponseEntity<Map<String, Object>> postAbsenWebApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postGeneralParamApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postReimburseApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        return null;
    }

    public ResponseEntity<Map<String, Object>> postSakitApproval(String tokenWithBearer, Long idApproval, ApprovalRequest request) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nikUser = jwtService.extractUsername(token);
                    Optional<KaryawanEntity> dataUser = karyawanRepository.findByNik(nikUser);
                    String namaUser = dataUser.get().getNama();
                    System.out.println(nikUser + "ini udah masuk postSakitApproval");

                    CutiAppEntity datanya = cutiAppRepository.findById(idApproval).get();
                    CutiEntity dataSakit = new CutiEntity();
                    if ("1".equals(request.getIsApprove())) {
                        datanya.setIsApproved("1");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppRepository.save(datanya);

                        dataSakit = CutiEntity.builder()
                        .alamatCuti(datanya.getAlamatCuti())
                        .dtmApp(datanya.getDtmApp())
                        .dtmCrt(datanya.getDtmCrt())
                        .flagApp("-")
                        .flgKet(datanya.getFlgKet())
                        .isApproved("1")
                        .jmlCuti(datanya.getJmlCuti())
                        .jmlCutiBersama(datanya.getJmlCutiBersama())
                        .jmlCutiKhusus(datanya.getJmlCutiKhusus())
                        .keperluanCuti(datanya.getKeperluanCuti())
                        .nama(datanya.getNama())
                        .nik(datanya.getNik())
                        .sisaCuti(datanya.getSisaCuti())
                        .tglKembaliKerja(datanya.getTglKembaliKerja())
                        .tglMulai(datanya.getTglMulai())
                        .tglSelesai(datanya.getTglSelesai())
                        .build();
                        cutiRepository.save(dataSakit);
                    }else if ("0".equals(request.getIsApprove())) {
                        datanya.setIsApproved("0");
                        datanya.setDtmApp(Timestamp.valueOf(LocalDateTime.now()));
                        datanya.setUsrApp(namaUser);
                        datanya.setNoteApp(request.getNoteApp());
                        cutiAppRepository.save(datanya);
                    }
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "berhasil post ke CutiEntity dan SetValue di CutiApp");
                    response.put("data", datanya);
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
