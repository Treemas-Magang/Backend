package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.UpdatePenempatanReq;
import com.treemaswebapi.treemaswebapi.controller.MemberController.request.MemberRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;
import com.treemaswebapi.treemaswebapi.service.AbsenService.MemberService;
import com.treemaswebapi.treemaswebapi.service.UpdateListProjectService.UpdateListProjectService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/absen")
@Data
@RequiredArgsConstructor
public class AbsenController {
    private final MemberService memberService;
    private final AbsenService absenService;
    private final UpdateListProjectService updatePenempatan;


    @GetMapping("/project-list")
    public ResponseEntity<Map<String, Object>> getProjectDetails(
        @RequestHeader("Authorization") String token
    ) {
        return absenService.getProjectDetails(token);
    }

    @PostMapping("/update-absen")
    public ResponseEntity<Map<String, Object>> updateProject(
        @RequestHeader("Authorization") String token,
        @RequestBody AbsenRequest updateRequest
    ) {
        return absenService.updateAbsen(token, updateRequest);
    }

    @PostMapping("/input-absen")
    public ResponseEntity<Map<String, Object>> inputAbsen(
        @RequestHeader("Authorization") String token,
        @RequestBody AbsenRequest request
    ) {
        return absenService.inputAbsen(token, request);
    }

    @PostMapping("/input-absen-pulang")
    public ResponseEntity<Map<String, Object>> inputAbsenPulang(
        @RequestHeader("Authorization") String token,
        @RequestBody AbsenRequest request
    ) {
        return absenService.inputAbsenPulang(token, request);
    }

    @GetMapping("/get-absen-belum-pulang")
    public ResponseEntity<Map<String, Object>> getUnprocessedAbsen(
        @RequestHeader("Authorization") String token
    ){
        return absenService.getUnprocessedAbsen(token);
    }


    @GetMapping("/get-member")
    public ResponseEntity<Map<String, Object>> getAbsenFromProjectId(
        @RequestHeader("Authorization") String token,
        @RequestBody MemberRequest request
    ){
        return memberService.getAbsenFromProjectId(token, request);
    }

    @GetMapping("/get-project-details")
    public ResponseEntity<Map<String, Object>> leaderProjectDetails(
        @RequestHeader("Authorization") String token,
        @RequestParam("projectId") String projectIdReq
    ){
        return memberService.leaderProjectDetails(token, projectIdReq);
    }

    @GetMapping("/get-all-projects")
    public ResponseEntity<Map<String,Object>> projectDetails(
        @RequestHeader("Authorization") String token
    ){
        return memberService.projectDetails(token);
    }

    @PatchMapping("/update-penempatan")
    public ResponseEntity<Map<String, Object>> updatePenempatan(
        @RequestHeader("Authorization") String token,
        @RequestBody UpdatePenempatanReq request
    ){
        return updatePenempatan.updateProject(token, request);
    }

    @GetMapping("/get-is-absen")
    public ResponseEntity<Map<String, Object>> getIsAbsen(
        @RequestHeader("Authorization") String token, LocalDate hariIni
    ){
        return absenService.getIsAbsen(token, hariIni);
    }



    // @GetMapping("/get-all-members")
    // public ResponseEntity<Map<String, Object>> headMemberDetails(
    //     @RequestHeader("Authorization") String token
    // ){
    //     return memberService.head
    // }
}   