package com.treemaswebapi.treemaswebapi.service.DetailData.CutiSakit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;
import com.treemaswebapi.treemaswebapi.repository.CutiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CutiSakitService {
    
    private final CutiRepository cutiRepository;

    public ResponseEntity<Map<String, Object>> cutiGet() {
        try {
            List<CutiEntity> cutiList  = cutiRepository.findByFlagApp("cuti");
            List<Map<String, Object>> responseData = new ArrayList<>();

            for (CutiEntity cuti : cutiList) {
                Map<String, Object> cutiData = new HashMap<>();
                cutiData.put("nik", cuti.getNik());
                cutiData.put("namaKaryawan", cuti.getNama());
                cutiData.put("tglMulai", cuti.getTglMulai());
                cutiData.put("tglSelesai", cuti.getTglSelesai());
                cutiData.put("tglMasuk", cuti.getTglKembaliKerja());
                cutiData.put("jmlCuti", cuti.getJmlCuti());
                cutiData.put("keperluanCuti", cuti.getKeperluanCuti());
                cutiData.put("status", cuti.getIsApproved());
                cutiData.put("usrapp", cuti.getUsrApp());
                cutiData.put("dtmapp", cuti.getDtmApp());
        
                responseData.add(cutiData);
            }
        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", responseData);
        
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve cuti");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> sakitGet() {
        try {
            List<CutiEntity> sakitList  = cutiRepository.findByFlagApp("sakit");
            List<Map<String, Object>> responseData = new ArrayList<>();

            for (CutiEntity sakit : sakitList) {
                Map<String, Object> sakitData = new HashMap<>();
                sakitData.put("nik", sakit.getNik());
                sakitData.put("namaKaryawan", sakit.getNama());
                sakitData.put("tglMulai", sakit.getTglMulai());
                sakitData.put("tglSelesai", sakit.getTglSelesai());
                sakitData.put("tglMasuk", sakit.getTglKembaliKerja());
                sakitData.put("jmlSakit", sakit.getJmlCuti());
                sakitData.put("keteranganSakit", sakit.getKeperluanCuti());
                sakitData.put("status", sakit.getIsApproved());
                sakitData.put("usrapp", sakit.getUsrApp());
                sakitData.put("dtmapp", sakit.getDtmApp());
        
                responseData.add(sakitData);
            }
        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", responseData);
        
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve cuti");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
