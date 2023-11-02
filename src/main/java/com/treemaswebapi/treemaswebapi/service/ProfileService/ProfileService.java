package com.treemaswebapi.treemaswebapi.service.ProfileService;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.ProfieController.request.ProfileRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanImageEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;
import com.treemaswebapi.treemaswebapi.repository.KaryawanImageRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final KaryawanRepository karyawanRepository;
    private final KaryawanImageRepository karyawanImageRepository;
    private final SysUserRepository sysUserRepository;
    private final JwtService jwtService;
    
    public ResponseEntity<Map<String, String>> updateProfile(
        ProfileRequest request, 
        @RequestHeader("Authorization") String jwtToken,
        @RequestPart(value = "foto", required = false) MultipartFile foto,
        @RequestPart(value = "fotoKtp", required = false) MultipartFile fotoKtp,
        @RequestPart(value = "fotoNpwp", required = false) MultipartFile fotoNpwp,
        @RequestPart(value = "fotoKk", required = false) MultipartFile fotoKk,
        @RequestPart(value = "fotoAsuransi", required = false) MultipartFile fotoAsuransi
        ) {
        try {

            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            System.out.println("INI TOKEN : "+token);
            System.out.println("INI USER DARI TOKEN : "+userToken);
            // User hasil compare nik dari request dengan database
            Optional<KaryawanEntity> nikKOptional = karyawanRepository.findByNik(userToken);

            if (nikKOptional.isPresent()) {
                KaryawanEntity nikK = nikKOptional.get();
                nikK.setNama(request.getNama());
                nikK.setTempatLahir(request.getTempatLahir());
                nikK.setTanggalLahir(request.getTanggalLahir());
                nikK.setJenisKelamin(request.getJenisKelamin());
                nikK.setAgama(request.getAgama());
                nikK.setKewarganegaraan(request.getKewarganegaraan());
                nikK.setKodePos(request.getKodePos());
                nikK.setAlamatKtp(request.getAlamatKtp());
                nikK.setNoHp(request.getNoHp());
                nikK.setEmail(request.getEmail());
                nikK.setNoRek(request.getNoRek());
                nikK.setNoNpwp(request.getNoNpwp());
                nikK.setJenjangPendidikan(request.getJenjangPendidikan());
                // Continue updating other fields as needed
                nikK.setTanggalBergabung(request.getTanggalBergabung());
                nikK.setAlamatSekarang(request.getAlamatSekarang());
                nikK.setStatusPerkawinan(request.getStatusPerkawinan());
                nikK.setGolonganDarah(request.getGolonganDarah());
                nikK.setNomorKtp(request.getNomorKtp());
                nikK.setEmergencyContact(request.getEmergencyContact());
                nikK.setStatusEmergency(request.getStatusEmergency());
                nikK.setAlamatEmergency(request.getAlamatEmergency());
                nikK.setTelpEmergency(request.getTelpEmergency());
                nikK.setProjectId(request.getProjectId());
                nikK.setDivisi(request.getDivisi());
                nikK.setNikLeader(request.getNikLeader());
                nikK.setIsLeader(request.getIsLeader());
                nikK.setUsrUpd(request.getUsrUpd());
                nikK.setHandsetImei(request.getHandsetImei());
                nikK.setHakCuti(request.getHakCuti());
                nikK.setIsKaryawan(request.getIsKaryawan());
                nikK.setHandsetImei(null);  // Example of setting a specific field to null

                karyawanRepository.save(nikK);
            }

            Optional<KaryawanImageEntity> nikKIOptional = karyawanImageRepository.findByNik(userToken);

            if(nikKIOptional.isPresent()) {
                KaryawanImageEntity nikKI = nikKIOptional.get();
                if (foto != null) {
                    nikKI.setFoto(convertToBase64(foto));
                    nikKI.setFotoPath(foto.getOriginalFilename());
                }
                
                // Handle 'fotoKtp' conditionally
                if (fotoKtp != null) {
                    nikKI.setFotoKtp(convertToBase64(fotoKtp));
                    nikKI.setFotoKtpPath(fotoKtp.getOriginalFilename());
                }
                
                // Handle 'fotoNpwp' conditionally
                if (fotoNpwp != null) {
                    nikKI.setFotoNpwp(convertToBase64(fotoNpwp));
                    nikKI.setFotoNpwpPath(fotoNpwp.getOriginalFilename());
                }
                
                // Handle 'fotoKk' conditionally
                if (fotoKk != null) {
                    nikKI.setFotoKk(convertToBase64(fotoKk));
                    nikKI.setFotoKkPath(fotoKk.getOriginalFilename());
                }
                
                // Handle 'fotoAsuransi' conditionally
                if (fotoAsuransi != null) {
                    nikKI.setFotoAsuransi(convertToBase64(fotoAsuransi));
                    nikKI.setFotoAsuransiPath(fotoAsuransi.getOriginalFilename());
                }
            
                karyawanImageRepository.save(nikKI);
            }

            Optional<SysUserEntity> userIdOptional = sysUserRepository.findByUserId(userToken);

            if(userIdOptional.isPresent()) {
                String isLeader = request.getIsLeader();
                if("1".equals(isLeader)) {
                    SysUserEntity userId = userIdOptional.get();
                    userId.setFullName(request.getNama());
                    userId.setRole(Role.LEADER);
                    userId.setIsLogin("0");

                    sysUserRepository.save(userId);
                } else {
                    SysUserEntity userId = userIdOptional.get();
                    userId.setFullName(request.getNama());
                    userId.setRole(Role.USER);
                    userId.setIsLogin("0");

                    sysUserRepository.save(userId);
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Update Success!");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "failed");
            response.put("message", "Update failed!");
            response.put("error", e.getMessage());

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