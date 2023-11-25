package com.treemaswebapi.treemaswebapi.controller.DashboardController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan.KaryawanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
 
    private final KaryawanService karyawanService;


    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> getDataPribadi(@RequestHeader("Authorization") String token, DashboardResponse dashboardResponse)
    {
        return karyawanService.getDataPribadi(token, dashboardResponse);
    }
}
