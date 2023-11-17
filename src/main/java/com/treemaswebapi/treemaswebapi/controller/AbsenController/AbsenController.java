package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treemaswebapi.treemaswebapi.controller.AbsenController.request.AbsenRequest;
import com.treemaswebapi.treemaswebapi.controller.MemberController.request.MemberRequest;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;
import com.treemaswebapi.treemaswebapi.service.AbsenService.MemberService;

@RestController
@RequestMapping("/api/absen")
public class AbsenController {
    private final MemberService memberService;
    private final AbsenService absenService;

    public AbsenController(AbsenService absenService, MemberService memberService) {
        this.absenService = absenService;
        this.memberService = memberService;
    }

    @GetMapping("/project-list")
    public ResponseEntity<Map<String, Object>> getProjectDetails(
        @RequestHeader("Authorization") String token
    ) {
        return absenService.getProjectDetails(token);
    }

    @PostMapping("/update-absen")
    public ResponseEntity<Map<String, Object>> updateProject(
        @RequestHeader("Authorization") String token,
        @RequestBody ProjectEntity projectId
    ) {
        return absenService.updateProject(token, projectId);
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

    // di bawah ini fungsi buat si leader

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
        @RequestBody MemberRequest request
    ){
        return memberService.leaderProjectDetails(token, request);
    }

    @GetMapping("/get-all-projects")
    public ResponseEntity<Map<String,Object>> headProjectDetails(
        @RequestHeader("Authorization") String token
    ){
        return memberService.headProjectDetails(token, null);
    }

    // @GetMapping("/get-all-members")
    // public ResponseEntity<Map<String, Object>> headMemberDetails(
    //     @RequestHeader("Authorization") String token
    // ){
    //     return memberService.head
    // }
}   