package com.treemaswebapi.treemaswebapi.controller.ReportData;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request.SakitRequest;
import com.treemaswebapi.treemaswebapi.controller.ReportData.request.ClaimRequestRD;
import com.treemaswebapi.treemaswebapi.service.ReportData.ClaimServiceRD;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/report-data")
@RequiredArgsConstructor
public class ClaimControllerRD {
    
    private final ClaimServiceRD service;

    @GetMapping("/claim-view")
    public ResponseEntity<Map<String, Object>> claimGetAll() {
        ResponseEntity<Map<String, Object>> response = service.claimGetAll();
        return response;
    }

    @PostMapping("/claim-form/add")
    public ResponseEntity<Map<String, Object>> claimAddMobile(
        @RequestHeader("Authorization") String jwtToken,
        @RequestBody ClaimRequestRD request
    ) {
        ResponseEntity<Map<String, Object>> response = service.claimAddMobile(jwtToken, request);
        return response;
    }
}
