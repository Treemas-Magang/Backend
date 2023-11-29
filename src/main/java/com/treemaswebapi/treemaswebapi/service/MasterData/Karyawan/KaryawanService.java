package com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.DashboardController.DashboardResponse;
import com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan.request.KaryawanAddRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanImageEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanImageRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
    public class KaryawanService {
        
        private final KaryawanRepository karyawanRepository;
        private final SysUserRepository sysUserRepository;
        private final KaryawanImageRepository karyawanImageRepository;
        private final AbsenRepository absenRepository;
        private final JwtService jwtService;

        private final PasswordEncoder passwordEncoder;

        public ResponseEntity<Map<String, String>> karyawanAdd(
            KaryawanAddRequest request,
            @RequestHeader("Authorization") String jwtToken
        ) {
            try {            
                // Mengirim ke table Karyawan
                 var karyawan = KaryawanEntity.builder()
                    .nik(request.getNik())
                    .nama(request.getNama())
                    .tempatLahir(request.getTempatLahir())
                    .tanggalLahir(request.getTanggalLahir())
                    .jenisKelamin(request.getJenisKelamin())
                    .agama(request.getAgama())
                    .kewarganegaraan(request.getKewarganegaraan())
                    .kodePos(request.getKodePos())
                    .alamatKtp(request.getAlamatKtp())
                    .noHp(request.getNoHp())
                    .noRek(request.getNoRek())
                    .noNpwp(request.getNoNpwp())
                    .jenjangPendidikan(request.getJenjangPendidikan())
                    .tanggalBergabung(request.getTanggalBergabung())
                    .alamatSekarang(request.getAlamatSekarang())
                    .statusPerkawinan(request.getStatusPerkawinan())
                    .golonganDarah(request.getGolonganDarah())
                    .nomorKtp(request.getNomorKtp())
                    .emergencyContact(request.getEmergencyContact())
                    .statusEmergency(request.getStatusEmergency())
                    .alamatEmergency(request.getAlamatEmergency())
                    .telpEmergency(request.getTelpEmergency())
                    .projectId(request.getProjectId())
                    .divisi(request.getDivisi())
                    .nikLeader(request.getNikLeader())
                    .isLeader(request.getIsLeader())
                    .usrUpd(request.getUsrUpd())
                    .handsetImei(request.getHandsetImei())
                    .hakCuti(request.getHakCuti())
                    .isKaryawan(request.getIsKaryawan())
                    .handsetImei(null)
                .build();
                karyawanRepository.save(karyawan);
                
                String isLeader = request.getIsLeader();
                System.out.println("LEADER : "+isLeader);
                // Mengirim ke table Sys_User
                if("1".equals(isLeader)) {
                    System.out.println("MASUK DIA LEADER");
                    var sysUser = SysUserEntity.builder()
                        .userId(request.getNik())
                        .email(request.getEmail())
                        .fullName(request.getNama())
                        .role(Role.LEADER)
                        .isLogin("0") // set ke 0 karena di table ini tidak boleh null
                        .sqlPassword(passwordEncoder.encode("123456"))
                    .build();
                    sysUserRepository.save(sysUser);
                } else {
                    System.out.println("MASUK DIA MEMBER");
                    var sysUser = SysUserEntity.builder()
                        .userId(request.getNik())
                        .email(request.getEmail())
                        .fullName(request.getNama())
                        .role(Role.USER)
                        .isLogin("0") // set ke 0 karena di table ini tidak boleh null
                        .sqlPassword(passwordEncoder.encode("123456"))
                    .build();
                    sysUserRepository.save(sysUser);
                }

                // Mengirim ke table Karyawan Image
                var karyawanImage = KaryawanImageEntity.builder()
                    .nik(request.getNik())
                    .foto(request.getFoto() != null ? request.getFoto() : null)
                    .fotoKtp(request.getFotoKtp() != null ? request.getFotoKtp() : null)
                    .fotoNpwp(request.getFotoNpwp() != null ? request.getFotoNpwp() : null)
                    .fotoKk(request.getFotoKk() != null ? request.getFotoKk() : null)
                    .fotoAsuransi(request.getFotoAsuransi() != null ? request.getFotoAsuransi() : null)
                    .fotoPath(request.getFotoPath() != null ? request.getFotoPath() : null)
                    .fotoKtpPath(request.getFotoKtpPath() != null ? request.getFotoKtpPath() : null)
                    .fotoNpwpPath(request.getFotoNpwpPath() != null ? request.getFotoNpwpPath() : null)
                    .fotoKkPath(request.getFotoKkPath() != null ? request.getFotoKkPath() : null)
                    .fotoAsuransiPath(request.getFotoAsuransiPath() != null ? request.getFotoAsuransiPath() : null)
                .build();
                karyawanImageRepository.save(karyawanImage);

            Map<String, String> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Registration Successful");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Registration Failed");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    public ResponseEntity<Map<String, Object>> karyawanGet() {
        try {
            List<KaryawanEntity> karyawan = karyawanRepository.findAll();

            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "Retrieved");
            response.put("data", karyawan);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed to retrieve karyawan");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }   

    public ResponseEntity<Map<String, String>> karyawanDelete(
        String id
    ) {
        try {
            // Cari Karyawan di tbl_karyawan berdasarkan ID
            Optional<KaryawanEntity> karyawanOptional = karyawanRepository.findByNik(id);
            Optional<SysUserEntity> sysUserOptional = sysUserRepository.findByUserId(id);
            Optional<KaryawanImageEntity> karyawanImageOptional = karyawanImageRepository.findByNik(id);
            if (karyawanOptional.isPresent()) {
                karyawanRepository.deleteById(id);
                sysUserRepository.deleteById(id);
                karyawanImageRepository.deleteById(id);
                Map<String, String> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "User deleted");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("status", "Failed");
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Delete User");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    public ResponseEntity<Map<String, Object>> getDataPribadi(@RequestHeader("Authorization") String tokenWithBearer, DashboardResponse dashboardResponse) {
        
        try{
            Map<String, Object> response = new HashMap<>();
            if (tokenWithBearer.startsWith("Bearer ")) {
            String token = tokenWithBearer.substring("Bearer ".length());
            String nik = jwtService.extractUsername(token);
            int masuk = absenRepository.countByJamMskIsNotNullAndNik(nik);
            int totalMasuk = absenRepository.countByIsAbsenAndNik("1", nik) + masuk;
            int totalSakit = absenRepository.countByIsSakitAndNik("1", nik);
            int totalTelatMasuk = absenRepository.countByNoteTelatMskIsNotNullAndNik(nik);
            int totalPulangCepat = absenRepository.countByNotePlgCepatIsNotNullAndNik(nik);
            int totalCuti = absenRepository.countByIsCutiAndNik("1", nik);
            int totalTidakMasuk = absenRepository.countByJamMskIsNullAndJamPlgIsNullAndNik(nik);
            dashboardResponse.setTotalMasuk(totalMasuk);
            dashboardResponse.setTotalSakit(totalSakit);
            dashboardResponse.setTotalTelatMasuk(totalTelatMasuk);
            dashboardResponse.setTotalPulangCepat(totalPulangCepat);
            dashboardResponse.setTotalCuti(totalCuti);
            dashboardResponse.setTotalTidakMasuk(totalTidakMasuk);

            response.put("success", true);
            response.put("message", "berhasil get data kehadiran karyawan");
            response.put("data", dashboardResponse);

            return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
            response.put("success", false);
            response.put("message", "Invalid Token");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}
