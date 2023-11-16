package com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request.CutiRequest;
import com.treemaswebapi.treemaswebapi.service.DetailData.CutiSakit.CutiSakitService;


@RestController
@RequestMapping("/api/detail-data")
public class CutiSakitController {
    private CutiSakitService service;

    @GetMapping("/cuti-view")
    public ResponseEntity<Map<String, Object>> cutiGet() {
        ResponseEntity<Map<String, Object>> response = service.cutiGet();
        return response;
    }

    @GetMapping("/sakit-view")
    public ResponseEntity<Map<String, Object>> sakitGet() {
        ResponseEntity<Map<String, Object>> response = service.sakitGet();
        return response;
    }

    // @PostMapping("/cuti-form/add")
    // public ResponseEntity<Map<String, Object>> cutiAdd(
    //     @RequestHeader("Authorization") String jwtToken,
    //     @RequestBody CutiRequest request
    // ) {
    //     ResponseEntity<Map<String, Object>> response = service.cutiAdd(jwtToken, request);
    //     return response;
    // }
}
