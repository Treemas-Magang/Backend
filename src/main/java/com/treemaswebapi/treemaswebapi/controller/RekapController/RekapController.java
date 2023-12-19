package com.treemaswebapi.treemaswebapi.controller.RekapController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treemaswebapi.treemaswebapi.service.RekapService.RekapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rekap")
@RequiredArgsConstructor
public class RekapController {
    private final RekapService rekapService;
    /*---------------------------INI BAGIAN REIMBURSE-------------------------- */
    @GetMapping("/get-rekap-reimburse")
    public ResponseEntity<Map<String, Object>> getReimburse(
        @RequestHeader("Authorization") String tokenWithBearer
    ){
        return rekapService.rekapReimburse(tokenWithBearer);
    }
    @GetMapping("/get-detail-reimburse")
    public ResponseEntity<Map<String, Object>> getDetailReimburse(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapReimburseDetail(tokenWithBearer, id);
    }
    /*---------------------------INI BAGIAN TIMESHEET-------------------------- */
    @GetMapping("/get-rekap-timesheet")
    public ResponseEntity<Map<String, Object>> getTimesheet(
        @RequestHeader("Authorization") String tokenWithBearer
    ) {
        return rekapService.rekapTimesheet(tokenWithBearer);
    }

    @GetMapping("/get-detail-timesheet")
    public ResponseEntity<Map<String, Object>> getDetailTimesheet(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapTimesheetDetail(tokenWithBearer, id);
    }
    /*---------------------------INI BAGIAN ABSEN---------------------------- */
    @GetMapping("/get-rekap-absen")
    public ResponseEntity<Map<String, Object>> getAbsen(
        @RequestHeader("Authorization") String tokenWithBearer
    ) {
        return rekapService.rekapAbsen(tokenWithBearer);
    }
    @GetMapping("/get-detail-absen")
    public ResponseEntity<Map<String, Object>> getDetailAbsen(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapAbsenDetail(tokenWithBearer, id);
    }
    /*---------------------------INI BAGIAN CUTI-------------------------- */
    @GetMapping("/get-rekap-cuti")
    public ResponseEntity<Map<String, Object>> getCuti(
        @RequestHeader("Authorization") String tokenWithBearer
    ) {
        return rekapService.rekapCuti(tokenWithBearer);
    }
    @GetMapping("/get-detail-cuti")
    public ResponseEntity<Map<String, Object>> getDetailCuti(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapCutiDetail(tokenWithBearer, id);
    }
    /*---------------------------INI BAGIAN SAKIT-------------------------- */
    @GetMapping("/get-rekap-sakit")
    public ResponseEntity<Map<String, Object>> getSakit(
        @RequestHeader("Authorization") String tokenWithBearer
    ) {
        return rekapService.rekapSakit(tokenWithBearer);
    }
     @GetMapping("/get-detail-sakit")
    public ResponseEntity<Map<String, Object>> getDetailSakit(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapSakitDetail(tokenWithBearer, id);
    }
    /*---------------------------INI BAGIAN CLAIM-------------------------- */
    @GetMapping("/get-rekap-claim")
    public ResponseEntity<Map<String, Object>> getClaim(
        @RequestHeader("Authorization") String tokenWithBearer
    ) {
        return rekapService.rekapClaim(tokenWithBearer);
    }
     @GetMapping("/get-detail-claim")
    public ResponseEntity<Map<String, Object>> getDetailClaim(
        @RequestHeader("Authorization") String tokenWithBearer,
        @RequestParam Long id
    ){
        return rekapService.rekapClaimDetail(tokenWithBearer, id);
    }
}
