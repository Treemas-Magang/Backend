package com.treemaswebapi.treemaswebapi.controller.NotifController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.service.NotifService.NotifService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/notif")
@Data
@RequiredArgsConstructor
public class NotifController {

    private final ProjectRepository projectRepository;
    private final NotifService notifService;
    
    // @GetMapping("/get-all-approval")
    // public ResponseEntity<Map<String, Object>> getAllApproval(
    //     @RequestHeader("Authorization") String tokenWithBearer
    // ){
    //     return notifService.getAllApproval(tokenWithBearer);
    // }
    @GetMapping("/get-approval")
    public ResponseEntity<Map<String, Object>> getApprovalBy(
            @RequestHeader("Authorization") String tokenWithBearer,
            @RequestParam("by") String by,
            @RequestParam("projectId") String projectIdString
    ) {
        ProjectEntity projectId = projectRepository.findByProjectId(projectIdString);
        switch (by.toLowerCase()) {

            // case libur ini adalah dapetin value approval buat orang yang kerja di hari sabtu dan minggu
            case "libur":
                return notifService.getLiburApproval(tokenWithBearer, projectIdString);
            
            case "lembur":
                return notifService.getLemburApproval(tokenWithBearer, projectIdString);

            case "cuti":
                return notifService.getCutiApproval(tokenWithBearer);

            case "cuti-web":
                return notifService.getCutiWebApproval(tokenWithBearer);

            case "absen-pulang":
                return notifService.getAbsenPulangApproval(tokenWithBearer, projectId);

            case "absen-web":
                return notifService.getAbsenWebApproval(tokenWithBearer, projectId);

            // case "general-param":
            //     return notifService.getGeneralParamApproval(tokenWithBearer);

            case "reimburse":
                return notifService.getReimburseApproval(tokenWithBearer, projectId);
            
            case "sakit":
                return notifService.getSakitApproval(tokenWithBearer);
            
            default:
                // Handle the case where 'by' is not recognized
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid value for 'by': " + by + "in the" + projectId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/get-data-count")
    public ResponseEntity<Map<String, Object>> getDataCount(@RequestHeader("Authorization") String tokenWithBearer) {
        return notifService.getFullCounter(tokenWithBearer);
    }
    
    @PostMapping("/post-approval")
    public ResponseEntity<Map<String, Object>> postApprovalBy(
            @RequestHeader("Authorization") String tokenWithBearer,
            @RequestParam("by") String by,
            @RequestParam("id") Long idApproval
    ) {
        switch (by.toLowerCase()) {

            // case libur ini adalah dapetin value approval buat orang yang kerja di hari sabtu dan minggu
            case "libur":
                return notifService.postLiburApproval(tokenWithBearer, idApproval);
            
            case "lembur":
                return notifService.postLemburApproval(tokenWithBearer, idApproval);

            case "cuti":
                return notifService.postCutiApproval(tokenWithBearer, idApproval);

            case "cuti-web":
                return notifService.postCutiWebApproval(tokenWithBearer, idApproval);

            case "absen-pulang":
                return notifService.postAbsenPulangApproval(tokenWithBearer, idApproval);

            case "absen-web":
                return notifService.postAbsenWebApproval(tokenWithBearer, idApproval);

            case "general-param":
                return notifService.postGeneralParamApproval(tokenWithBearer, idApproval);

            case "reimburse":
                return notifService.postReimburseApproval(tokenWithBearer, idApproval);
            
            case "sakit":
                return notifService.postSakitApproval(tokenWithBearer, idApproval);
            
            default:
                // Handle the case where 'by' is not recognized
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid value for 'by': " + by + "in the" + idApproval);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/get-detail-approval")
    public ResponseEntity<Map<String, Object>> getDetailApprovalBy(
            @RequestHeader("Authorization") String tokenWithBearer,
            @RequestParam("by") String by,
            @RequestParam("id") Long idApproval
    ) {
        switch (by.toLowerCase()) {

            // case libur ini adalah dapetin value approval buat orang yang kerja di hari sabtu dan minggu
            case "libur":
                return notifService.getDetailLiburApproval(tokenWithBearer, idApproval);
            
            case "lembur":
                return notifService.getDetailLemburApproval(tokenWithBearer, idApproval);

            case "cuti":
                return notifService.getDetailCutiApproval(tokenWithBearer, idApproval);

            case "cuti-web":
                return notifService.getDetailCutiWebApproval(tokenWithBearer, idApproval);

            case "absen-pulang":
                return notifService.getDetailAbsenPulangApproval(tokenWithBearer, idApproval);

            case "absen-web":
                return notifService.getDetailAbsenWebApproval(tokenWithBearer, idApproval);

            // case "general-param":
            //     return notifService.getDetailGeneralParamApproval(tokenWithBearer, idApproval);

            case "reimburse":
                return notifService.getDetailReimburseApproval(tokenWithBearer, idApproval);
            
            case "sakit":
                return notifService.getDetailSakitApproval(tokenWithBearer, idApproval);
            
            default:
                // Handle the case where 'by' is not recognized
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid value for 'by': " + by + "in the" + idApproval);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
}
