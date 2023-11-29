package com.treemaswebapi.treemaswebapi.controller.Dashboard;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.Dashboard.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService service;

    @GetMapping("/data-kehadiran")
    public ResponseEntity<Map<String, Object>> dashboardGet(
        @RequestHeader("Authorization") String jwtToken,
        DashboardResponse dashboardResponse
        ) {
        ResponseEntity<Map<String, Object>> response = service.dashboardGet(jwtToken, dashboardResponse);
        return response;
    }

    @GetMapping("/navbar")
    public ResponseEntity<Map<String, Object>> dashboardGetFoto(
        @RequestHeader("Authorization") String jwtToken
        ) {
        ResponseEntity<Map<String, Object>> response = service.dashboardGetFoto(jwtToken);
        return response;
    }

    // @GetMapping("/data-kehadiran-member")
    // public ResponseEntity<Map<String, Object>> dashboardGetMember(
    //     DashboardResponse dashboardResponse
    //     ) {
    //     ResponseEntity<Map<String, Object>> response = service.dashboardGetMember(dashboardResponse);
    //     return response;
    // }
}
