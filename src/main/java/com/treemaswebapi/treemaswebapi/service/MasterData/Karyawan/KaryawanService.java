package com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
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
            @RequestPart(value = "foto", required = false) MultipartFile foto,
            @RequestPart(value = "fotoKtp", required = false) MultipartFile fotoKtp,
            @RequestPart(value = "fotoNpwp", required = false) MultipartFile fotoNpwp,
            @RequestPart(value = "fotoKk", required = false) MultipartFile fotoKk,
            @RequestPart(value = "fotoAsuransi", required = false) MultipartFile fotoAsuransi
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
                    .email(request.getEmail())
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
                    .foto(foto != null ? convertToBase64(foto) : null)
                    .fotoKtp(fotoKtp != null ? convertToBase64(fotoKtp) : null)
                    .fotoNpwp(fotoNpwp != null ? convertToBase64(fotoNpwp) : null)
                    .fotoKk(fotoKk != null ? convertToBase64(fotoKk) : null)
                    .fotoAsuransi(fotoAsuransi != null ? convertToBase64(fotoAsuransi) : null)
                    .fotoPath(foto != null ? foto.getOriginalFilename() : null)
                    .fotoKtpPath(fotoKtp != null ? fotoKtp.getOriginalFilename() : null)
                    .fotoNpwpPath(fotoNpwp != null ? fotoNpwp.getOriginalFilename() : null)
                    .fotoKkPath(fotoKk != null ? fotoKk.getOriginalFilename() : null)
                    .fotoAsuransiPath(fotoAsuransi != null ? fotoAsuransi.getOriginalFilename() : null)
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
    // Helper method to convert MultipartFile to Base64
    private String convertToBase64(MultipartFile file) {
        try {
            if (file != null) {
                byte[] bytes = file.getBytes();
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (IOException e) {
            // Handle the exception, for example, log it
            e.printStackTrace();
        }
        return null; // or an empty string if needed
    }
    public ResponseEntity<Map<String, Object>> getDataPribadi(@RequestHeader("Authorization") String tokenWithBearer, DashboardResponse dashboardResponse) {
        
        try{
            Map<String, Object> response = new HashMap<>();
            if (tokenWithBearer.startsWith("Bearer ")) {
            String token = tokenWithBearer.substring("Bearer ".length());
            String nik = jwtService.extractUsername(token);
            int masuk = absenRepository.countByJamMskIsNotNullAndNik(nik);
            int totalMasuk = masuk;
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

    // Helper method to getFile path
    // public String saveFile(MultipartFile file) {
    // try {
    //     // Tentukan lokasi penyimpanan file
    //     String uploadDirectory = "lokasi/penyimpanan/file";
        
    //     // Mendapatkan nama file asli
    //     String originalFilename = file.getOriginalFilename();
        
    //     // Menggabungkan lokasi penyimpanan dengan nama file
    //     Path filePath = Paths.get(uploadDirectory, originalFilename);
        
    //     // Simpan file ke path yang ditentukan
    //     file.transferTo(filePath.toFile());
        
    //     // Mengembalikan path file
    //     return filePath.toString();
    // } catch (IOException e) {
    //     // Tangani kesalahan jika gagal menyimpan file
    //     e.printStackTrace();
    //     return null;
    // }
//}



}
