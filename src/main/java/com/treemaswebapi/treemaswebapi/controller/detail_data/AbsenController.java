package com.treemaswebapi.treemaswebapi.controller.detail_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.service.AbsenService;

@RestController
@RequestMapping("/api/detail-data")
public class AbsenController {

    @Autowired
    private AbsenService absenService;
    // Dari Android
    @PostMapping("/absen-form")
    private ResponseEntity<String> postAbsen(@RequestBody AbsenEntity absenData){
        try {
            absenService.absenUser(absenData);
            return ResponseEntity.status(HttpStatus.CREATED).body("Absen Created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create absen "+e.getMessage());
        }
    }

    @GetMapping("/absen-view")
    private ResponseEntity<String> getAbsen(){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body("Absen Created!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create absen "+e.getMessage());
        }
    }
}
