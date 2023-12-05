package com.treemaswebapi.treemaswebapi.service.ProfileService;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.treemaswebapi.treemaswebapi.controller.ProfileController.request.ProfileRequest;
import com.treemaswebapi.treemaswebapi.entity.JabatanEntity.JabatanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanImageEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;
import com.treemaswebapi.treemaswebapi.repository.JabatanRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanImageRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.ProjectRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final KaryawanRepository karyawanRepository;
    private final KaryawanImageRepository karyawanImageRepository;
    private final SysUserRepository sysUserRepository;
    private final JwtService jwtService;
    private final JabatanRepository jabatanRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ProjectRepository projectRepository;

    public ResponseEntity<Map<String, String>> updateProfile(
        ProfileRequest request, 
        @RequestHeader("Authorization") String jwtToken
        ) {
        try {

            // Cari siapa yang akses api ini
            String token = jwtToken.substring(7);
            String userToken = jwtService.extractUsername(token);
            
            System.out.println("INI TOKEN : "+token);
            System.out.println("INI USER DARI TOKEN : "+userToken);
            // User hasil compare nik dari request dengan database
            Optional<KaryawanEntity> nikKOptional = karyawanRepository.findByNik(userToken);
            // Cari jabatanId di tbl_jabatan
                JabatanEntity jabatan = jabatanRepository.findById(request.getSelectedRole())
                    .orElseThrow(() -> new RuntimeException("TipeClaim not found for id: " + request.getSelectedRole()));

            // Cari projectId di tbl_project;
            ProjectEntity project = projectRepository.findById(request.getSelectedProject())
                    .orElseThrow(() -> new RuntimeException("Project not found for id: " + request.getSelectedProject()));

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
                nikK.setProjectId(project);
                nikK.setDivisi(request.getDivisi());
                nikK.setNikLeader(request.getNikLeader());
                nikK.setIsLeader(request.getIsLeader());
                nikK.setUsrUpd(request.getUsrUpd());
                nikK.setHandsetImei(request.getHandsetImei());
                nikK.setHakCuti(request.getHakCuti());
                nikK.setIsKaryawan(request.getIsKaryawan());

                karyawanRepository.save(nikK);
            }

            Optional<KaryawanImageEntity> nikKIOptional = karyawanImageRepository.findByNik(userToken);
            
            if(nikKIOptional.isPresent()) {
                KaryawanImageEntity nikKI = nikKIOptional.get();
                nikKI.setFoto(request.getFoto() != null ? request.getFoto() : null);
                nikKI.setFotoKtp(request.getFotoKtp() != null ? request.getFotoKtp() : null);
                nikKI.setFotoNpwp(request.getFotoNpwp() != null ? request.getFotoNpwp() : null);
                nikKI.setFotoKk(request.getFotoKk() != null ? request.getFotoKk() : null);
                nikKI.setFotoAsuransi(request.getFotoAsuransi() != null ? request.getFotoAsuransi() : null);
                nikKI.setFotoPath(request.getFotoPath() != null ? request.getFotoPath() : null);
                nikKI.setFotoKtpPath(request.getFotoKtpPath() != null ? request.getFotoKtpPath() : null);
                nikKI.setFotoNpwpPath(request.getFotoNpwpPath() != null ? request.getFotoNpwpPath() : null);
                nikKI.setFotoKkPath(request.getFotoKkPath() != null ? request.getFotoKkPath() : null);
                nikKI.setFotoAsuransiPath(request.getFotoAsuransiPath() != null ? request.getFotoAsuransiPath() : null);;
                                
                karyawanImageRepository.save(nikKI);
            }
            Optional<KaryawanEntity> user = karyawanRepository.findByNik(userToken);
            String nama = user.get().getNama();

            Optional<SysUserEntity> userIdOptional = sysUserRepository.findByUserId(userToken);
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));
            if(userIdOptional.isPresent()) {
                    SysUserEntity userId = userIdOptional.get();
                    userId.setFullName(request.getNama());
                    userId.setRole(jabatan);
                    userId.setUsrUpd(nama);
                    userId.setDtmUpd(dtmCrt);

                    // Ambil password dari user
                    String storedPassword = userId.getSqlPassword();

                    // Set Password kalau old Pw dari request sesuai dengan pw di database

                if (passwordEncoder.matches(request.getOldPassword(), storedPassword))
                {
                    System.out.println("Masuk Compare");
                    if (request.getNewPassword().equals(request.getConfPassword())) 
                    {
                        userId.setSqlPassword(passwordEncoder.encode(request.getNewPassword()));
                        userId.setIsPassChg("1");
                        userId.setLastPasschg(dtmCrt);
                    } else {
                        Map<String, String> response = new HashMap<>();
                        response.put("failed", "failed");
                        response.put("message", "Sorry, the new password and confirmation password don't match. Please make sure they match to proceed.");

                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    }
                } else {
                    Map<String, String> response = new HashMap<>();
                    response.put("status", "failed");
                    response.put("message", "Old password is incorrect.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                    sysUserRepository.save(userId);

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