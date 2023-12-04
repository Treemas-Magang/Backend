package com.treemaswebapi.treemaswebapi.service.NotifService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.NotifController.response.ApprovalResponse;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.AbsenPulangAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiAppUploadRepository;
import com.treemaswebapi.treemaswebapi.repository.GeneralParamApprovalRepository;
import com.treemaswebapi.treemaswebapi.repository.ReimburseAppRepository;

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

     public ResponseEntity<Map<String, Object>> getAllApproval(@RequestHeader String tokenWithBearer) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(absenAppRepository.findAll())
                    .absenPulangApproval(absenPulangAppRepository.findAll())
                    .absenPulangWebApproval(absenAppUploadRepository.findAll())
                    .cutiApproval(cutiAppRepository.findAll())
                    .cutiApprovalWeb(cutiAppUploadRepository.findAll())
                    .generalParamApproval(generalParamAppRepository.findAll())
                    .reimburseApproval(reimburseAppRepository.findAll())
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

    public ResponseEntity<Map<String, Object>> getAbsenApproval(String tokenWithBearer) {
         try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(absenAppRepository.findAll())
                    .absenPulangApproval(null)
                    .absenPulangWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
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

    public ResponseEntity<Map<String, Object>> getCutiApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenPulangWebApproval(null)
                    .cutiApproval(cutiAppRepository.findAll())
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
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
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenPulangWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(cutiAppUploadRepository.findAll())
                    .generalParamApproval(null)
                    .reimburseApproval(null)
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

    public ResponseEntity<Map<String, Object>> getAbsenPulangApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(absenPulangAppRepository.findAll())
                    .absenPulangWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(null)
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

    // public ResponseEntity<Map<String, Object>> getAbsenPulangWebApproval(String tokenWithBearer) {
    //     try {
    //             if (tokenWithBearer.startsWith("Bearer ")) {
    //                 String token = tokenWithBearer.substring("Bearer ".length());
    //                 String nik = jwtService.extractUsername(token);
    //                 System.out.println(nik);
    //                 ApprovalResponse approvalResponse = ApprovalResponse.builder()
    //                 .absenApproval(null)
    //                 .absenPulangApproval(null)
    //                 .absenPulangWebApproval(absenPulangAppUploadRepository.findAll())
    //                 .cutiApproval(null)
    //                 .cutiApprovalWeb(null)
    //                 .generalParamApproval(null)
    //                 .reimburseApproval(null)
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
    // }

    public ResponseEntity<Map<String, Object>> getGeneralParamApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(null)
                    .absenPulangApproval(null)
                    .absenPulangWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(generalParamAppRepository.findAll())
                    .reimburseApproval(null)
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

    public ResponseEntity<Map<String, Object>> getReimburseParamApproval(String tokenWithBearer) {
        try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);
                    System.out.println(nik);
                    ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .absenApproval(absenAppRepository.findAll())
                    .absenPulangApproval(null)
                    .absenPulangWebApproval(null)
                    .cutiApproval(null)
                    .cutiApprovalWeb(null)
                    .generalParamApproval(null)
                    .reimburseApproval(reimburseAppRepository.findAll())
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
