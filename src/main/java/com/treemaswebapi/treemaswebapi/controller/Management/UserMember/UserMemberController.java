package com.treemaswebapi.treemaswebapi.controller.Management.UserMember;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.Dashboard.DashboardResponse;
import com.treemaswebapi.treemaswebapi.service.Management.UserMember.UserMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class UserMemberController {
    private final UserMemberService service;
    
    @GetMapping("/user-member-view")
    public ResponseEntity<Map<String, Object>> userMemberAllUser(
        @RequestHeader("Authorization") String jwtToken
        ) {
        ResponseEntity<Map<String, Object>> response = service.userMemberAllUser(jwtToken);
        return response;
    }

    @GetMapping("/user-member-view/dropdown")
    public ResponseEntity<Map<String, Object>> dropdownUser() {
        ResponseEntity<Map<String, Object>> response = service.dropdownUser();
        return response;
    }

} 
