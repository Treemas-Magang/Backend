package com.treemaswebapi.treemaswebapi.service.DetailData.CutiSakit;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request.CutiApprove;
import com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request.CutiRequest;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;
import com.treemaswebapi.treemaswebapi.entity.CutiEntity.MasterCutiEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.repository.CutiAppRepository;
import com.treemaswebapi.treemaswebapi.repository.CutiRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.MasterCutiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CutiSakitService {
    
    private final CutiRepository cutiRepository;
    private final KaryawanRepository karyawanRepository;
    private final JwtService jwtService;
    private final CutiAppRepository cutiAppRepository;
    private final MasterCutiRepository masterCutiRepository;

    public ResponseEntity<Map<String, Object>> cutiGet() {
        try {
            // Disetujui atau ditolak
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

            // Menunggu
            List<CutiAppEntity> cutiAppList  = cutiAppRepository.findByFlgKet("cuti");
            List<Map<String, Object>> responseDataApp = new ArrayList<>();

            for (CutiAppEntity cutiApp : cutiAppList) {
                Map<String, Object> cutiDataApp = new HashMap<>();
                cutiDataApp.put("nik", cutiApp.getNik());
                cutiDataApp.put("namaKaryawan", cutiApp.getNama());
                cutiDataApp.put("tglMulai", cutiApp.getTglMulai());
                cutiDataApp.put("tglSelesai", cutiApp.getTglSelesai());
                cutiDataApp.put("tglMasuk", cutiApp.getTglKembaliKerja());
                cutiDataApp.put("jmlCuti", cutiApp.getJmlCuti());
                cutiDataApp.put("keperluanCuti", cutiApp.getKeperluanCuti());
                cutiDataApp.put("status", cutiApp.getIsApproved());
                cutiDataApp.put("usrapp", cutiApp.getUsrApp());
                cutiDataApp.put("dtmapp", cutiApp.getDtmApp());
        
                responseDataApp.add(cutiDataApp);
            }

            Map<String, Object> dataApporveOrNoOrWait = new HashMap<>();
            dataApporveOrNoOrWait.put("setujuAtauTolak", responseData);
            dataApporveOrNoOrWait.put("menunggu", responseDataApp);
        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", dataApporveOrNoOrWait);

           
            
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

    public ResponseEntity<Map<String, Object>> userCutiAdd(
        @RequestHeader("Authorization") String jwtToken,
        CutiRequest request
    ) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));

            MasterCutiEntity masterCutiEntity = masterCutiRepository.findById(request.getSelectedMasterCutiId())
                .orElseThrow(() -> new RuntimeException("MasterCuti not found for id: " + request.getSelectedMasterCutiId()));

            // Cari hakcuti dari table karyawan dan tempatkan ke tbl cuti di sisa cuti
            BigDecimal hakCuti = karyawanRepository.findByNik(userToken)
                .orElseThrow(() -> new RuntimeException("LEAVE not found"))
                .getHakCuti();

            // Ke tbl_cuti_app
            var cutiApp = CutiAppEntity.builder()
                .nik(userToken)
                .nama(nama)
                .tglMulai(request.getTglMulai())
                .tglSelesai(request.getTglSelesai())
                .tglKembaliKerja(request.getTglKembaliKerja())
                .keperluanCuti(request.getKeperluanCuti())
                .alamatCuti(request.getAlamatCuti())
                .jmlCutiBersama(request.getJmlCutiBersama())
                .jmlCutiKhusus(request.getJmlCutiKhusus())
                .flgKet("cuti")
                .dtmCrt(dtmCrt)
                .usrCrt(nama)
                .jmlCuti(request.getJmlCuti())
                .masterCutiEntity(masterCutiEntity)
                .sisaCuti(hakCuti)
            .build();

            cutiAppRepository.save(cutiApp);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Cuti Created");
            response.put("data", cutiApp);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Create Cuti");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> cutiApprove(
        @RequestHeader("Authorization") String jwtToken,
        CutiApprove request,
        Long id
    ) {
        try {
            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);

            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();
            Optional<CutiAppEntity> cutiApp = cutiAppRepository.findById(id);

            if(cutiApp.isPresent()) {
                var cutiAppList = cutiApp.get();
                // Orang Lain
                String nikCutiUser = cutiApp.get().getNik();
                Optional<KaryawanEntity> nikOther = karyawanRepository.findByNik(nikCutiUser);

                BigDecimal hakCuti = karyawanRepository.findByNik(nikCutiUser)
                .orElseThrow(() -> new RuntimeException("LEAVE not found"))
                .getHakCuti();

                long currentTimeMillis = System.currentTimeMillis();
                Timestamp dtmApp = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));

                MasterCutiEntity masterCutiEntity = cutiAppList.getMasterCutiEntity(); // Retrieve from CutiAppEntity

                CutiEntity cutiApproved = new CutiEntity();
                cutiApproved.setUsrApp(nama);
                cutiApproved.setDtmApp(dtmApp);
                cutiApproved.setNoteApp(request.getNoteApp());
                cutiApproved.setIsApproved(request.getIsApproved());
                cutiApproved.setNik(cutiAppList.getNik());
                cutiApproved.setNama(cutiAppList.getNama());
                cutiApproved.setTglMulai(cutiAppList.getTglMulai());
                cutiApproved.setTglSelesai(cutiAppList.getTglSelesai());
                cutiApproved.setTglKembaliKerja(cutiAppList.getTglKembaliKerja());
                cutiApproved.setJmlCuti(cutiAppList.getJmlCuti());
                cutiApproved.setKeperluanCuti(cutiAppList.getKeperluanCuti());
                cutiApproved.setAlamatCuti(cutiAppList.getAlamatCuti());
                cutiApproved.setFlgKet(cutiAppList.getFlgKet());
                cutiApproved.setDtmCrt(cutiAppList.getDtmCrt());
                cutiApproved.setUsrCrt(cutiAppList.getUsrCrt());
                cutiApproved.setMasterCutiEntity(masterCutiEntity);
                cutiApproved.setSisaCuti(cutiAppList.getSisaCuti());
                // If isApproved is "1", decrement hakCuti by 1
                String isApproved = request.getIsApproved();
                if ("1".equals(isApproved)) {
                    BigDecimal updatedHakCuti = hakCuti.subtract(BigDecimal.ONE);
                    nikOther.get().setHakCuti(updatedHakCuti);
                    karyawanRepository.save(nikOther.get());
                    cutiApproved.setSisaCuti(updatedHakCuti);
                }

                cutiRepository.save(cutiApproved);
                cutiAppRepository.delete(cutiAppList);
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "Cuti Approved");
                response.put("data", cutiApproved);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Failed");
                response.put("message", "Cuti Not Found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Approve Cuti");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
