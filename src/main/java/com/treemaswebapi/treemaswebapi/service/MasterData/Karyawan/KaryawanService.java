package com.treemaswebapi.treemaswebapi.service.MasterData.Karyawan;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.controller.MasterData.Karyawan.request.KaryawanAddRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanImageEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity;
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

        private final PasswordEncoder passwordEncoder;

        public ResponseEntity<Map<String, String>> karyawanAdd(
            KaryawanAddRequest request,
            MultipartFile foto,
            MultipartFile fotoKtp,
            MultipartFile fotoNpwp,
            MultipartFile fotoKk,
            MultipartFile fotoAsuransi
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
                .build();
                
                // Mengirim ke table Sys_User
                var sysUser = SysUserEntity.builder()
                    .userid(request.getNik())
                    .role(request.getRole())
                    .isLogin("0")
                    .sqlPassword(passwordEncoder.encode("123456"))
                .build();

                // Mengirim ke table Karyawan Image
                var karyawanImage = KaryawanImageEntity.builder()
                    .nik(request.getNik())
                    .foto(convertToBase64(foto))
                    .fotoKtp(convertToBase64(fotoKtp))
                    .fotoNpwp(convertToBase64(fotoNpwp))
                    .fotoKk(convertToBase64(fotoKk))
                    .fotoAsuransi(convertToBase64(fotoAsuransi))
                .build();

                System.out.println("Image : "+foto.getOriginalFilename()+" "+fotoKtp.getOriginalFilename()+" "+fotoNpwp.getOriginalFilename()+" "+fotoKk.getOriginalFilename()+" "+fotoAsuransi.getOriginalFilename());

                            
            // Save each request to each table
                karyawanRepository.save(karyawan);
                sysUserRepository.save(sysUser);
                karyawanImageRepository.save(karyawanImage);



            Map<String, String> response = new HashMap();
            response.put("status", "Success");
            response.put("message", "Registration Successful");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap();
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
}
