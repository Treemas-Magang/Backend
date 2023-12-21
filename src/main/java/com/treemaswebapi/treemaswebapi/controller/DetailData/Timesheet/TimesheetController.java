package com.treemaswebapi.treemaswebapi.controller.DetailData.Timesheet;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet.TimesheetResponse;
import com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet.TimesheetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/detail-data")
@RequiredArgsConstructor
public class TimesheetController {
    
    private final TimesheetService service;

    @GetMapping("/timesheet-view")
    public ResponseEntity<Map<String, Object>> timesheetGet(@RequestHeader("Authorization") String jwtToken,TimesheetResponse timesheetResponse) {
        ResponseEntity<Map<String, Object>> response = service.timesheetGet(jwtToken,timesheetResponse);
        return response;
    }
}
