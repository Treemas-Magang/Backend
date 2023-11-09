package com.treemaswebapi.treemaswebapi.controller.MasterData.Jabatan;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Cuti.request.MasterCutiRequest;
import com.treemaswebapi.treemaswebapi.controller.MasterData.Jabatan.request.JabatanRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Jabatan.JabatanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master-data")
@RequiredArgsConstructor
public class JabatanController {
    
    private final JabatanService service;

    @PostMapping("/jabatan-form/add")
    ResponseEntity<Map<String, Object>> cutiAdd(
        @RequestBody JabatanRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.jabatanAdd(request);
        return response;  
    }
}
