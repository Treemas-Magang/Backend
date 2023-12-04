package com.treemaswebapi.treemaswebapi.service.NotifService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.NotifController.response.ApprovalResponse;
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
    private AbsenAppRepository absenAppRepository;
    private CutiAppRepository cutiAppRepository;
    private CutiAppUploadRepository cutiAppUploadRepository;
    private AbsenAppUploadRepository absenAppUploadRepository;
    private AbsenPulangAppRepository absenPulangAppRepository;
    private GeneralParamApprovalRepository generalParamAppRepository;
    private ReimburseAppRepository reimburseAppRepository;

     public ResponseEntity<Map<String, Object>> getAllApproval(@RequestHeader String tokenWithBearer, @RequestParam("date") LocalDate date) {
            try {
                if (tokenWithBearer.startsWith("Bearer ")) {
                    String token = tokenWithBearer.substring("Bearer ".length());
                    String nik = jwtService.extractUsername(token);

                    ApprovalResponse approvalResponse = new ApprovalResponse();

                    approvalResponse.setAbsenApproval(absenAppRepository.findAll());
                    approvalResponse.setAbsenPulangApproval(absenPulangAppRepository.findAll());
                    approvalResponse.setAbsenPulangWebApproval(absenAppUploadRepository.findAll());
                    approvalResponse.setCutiApproval(cutiAppRepository.findAll());
                    approvalResponse.setCutiApprovalWeb(cutiAppUploadRepository.findAll());
                    approvalResponse.setGeneralParamApproval(generalParamAppRepository.findAll());
                    approvalResponse.setReimburseApproval(reimburseAppRepository.findAll());

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
