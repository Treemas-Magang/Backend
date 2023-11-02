package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;

@RestController
@RequestMapping("/api/absen")
public class AbsenController {

    private final AbsenService service;

    public AbsenController(AbsenService service) {
        this.service = service;
    }

    @GetMapping("/project-list")
    public ResponseEntity<?> getProjectList(
        @RequestHeader("Authorization") String token
    ) {
        return service.getProjectDetails(token);
    }
}
