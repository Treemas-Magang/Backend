package com.treemaswebapi.treemaswebapi.controller.NotifController;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.NotifService.NotifService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notif")
@RequiredArgsConstructor
public class NotifController {

    private final NotifService notifService;
    
    @GetMapping("/get-all-approval")
    public ResponseEntity<Map<String, Object>> getProject(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam("date") LocalDate date
    ){
        return notifService.getAllApproval(tokenWithBearer, date);
    }
}
