package com.treemaswebapi.treemaswebapi.controller.MasterData.Jabatan;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Jabatan.request.JabatanRequest;
import com.treemaswebapi.treemaswebapi.service.MasterData.Jabatan.JabatanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/master-data")
@RequiredArgsConstructor
public class JabatanController {
    
    private final JabatanService service;

    @PostMapping("/jabatan-form/add")
    ResponseEntity<Map<String, Object>> jabatanAdd(
        @RequestBody JabatanRequest request
    ) {
        ResponseEntity<Map<String, Object>> response = service.jabatanAdd(request);
        return response;  
    }

    @GetMapping("/jabatan-view")
    public ResponseEntity<Map<String, Object>> jabatanGet() {
        ResponseEntity<Map<String,Object>> response = service.jabatanGet();
        return response;
    }
}
