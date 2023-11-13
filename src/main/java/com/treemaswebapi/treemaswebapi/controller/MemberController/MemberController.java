package com.treemaswebapi.treemaswebapi.controller.MemberController;

import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.service.AbsenService.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/leader-project-details")
    public ResponseEntity<Map<String, Object>> leaderProjectDetails(
            @RequestParam String projectId,
            @RequestHeader("Authorization") String tokenWithBearer) {
        return memberService.leaderProjectDetails(projectId, tokenWithBearer);
    }

    @GetMapping("/get-absen-from-project")
    public ResponseEntity<Map<String, Object>> getAbsenFromProjectId(
            @RequestParam ProjectEntity projectId,
            @RequestHeader("Authorization") String tokenWithBearer) {
                LocalDate targetDate = LocalDate.now();
        return memberService.getAbsenFromProjectId(projectId, tokenWithBearer, targetDate);
    }
}
