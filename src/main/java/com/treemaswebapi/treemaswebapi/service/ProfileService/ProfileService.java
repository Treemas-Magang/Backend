package com.treemaswebapi.treemaswebapi.service.ProfileService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.controller.ProfieController.ProfileRequest;
import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public ResponseEntity<Map<String, Object>> updateProfile(ProfileRequest request) {
        try {
            // User hasil compare nik dari request dengan database
            Optional<UserEntity> optionalUser = userRepository.findByNik(request.getNik());

            // Kalau Usernya ditemukan di database
            if (optionalUser.isPresent()) {
                // Ambil data user di database
                UserEntity user = optionalUser.get();
                // Ambil password dari user
                String storedPassword = user.getPassword();

                // Set Password kalau old Pw dari request sesuai dengan pw di database
                if(passwordEncoder.matches(request.getOldPassword(), storedPassword)) {
                    System.out.println("Masuk Compare");
                    if(request.getNewPassword() != null && request.getNewPassword().equals(request.getConfirmPassword()))
                        {
                            if (passwordEncoder.matches(request.getNewPassword(), storedPassword)) {
                    
                                Map<String, Object> response = new HashMap<>();
                                response.put("success", true);
                                response.put("message", "You're using your old password!");

                                return ResponseEntity.status(HttpStatus.OK).body(response);
                            }
                            var updatedUser = UserEntity.builder()
                            .nik(request.getNik())
                            .namaKaryawan(request.getNamaKaryawan())
                            .password(passwordEncoder.encode(request.getNewPassword()))
                            .email(request.getEmail())
                            .role(request.getRole())
                            .agama(request.getAgama())
                            .noKtp(request.getNoKtp())
                            .noNpwp(request.getNoNpwp())
                            .noTelepon(request.getNoTelepon())
                            .tempatLahir(request.getTempatLahir())
                            .tanggalLahir(request.getTanggalLahir())
                            .jenisKelamin(request.getJenisKelamin())
                            .golDarah(request.getGolDarah())
                            .statusNikah(request.getStatusNikah())
                            .jenjangPendidikan(request.getJenjangPendidikan())
                            .noRekening(request.getNoRekening())
                            .kewarganegaraan(request.getKewarganegaraan())
                            .alamatKtp(request.getAlamatKtp())
                            .alamatSekarang(request.getAlamatSekarang())
                            .kodePos(request.getKodePos())
                            .kontakDarurat(request.getKontakDarurat())
                            .statusKontak(request.getStatusKontak())
                            .alamatKontak(request.getAlamatKontak())
                            .teleponDarurat(request.getTeleponDarurat())
                            .deviceId(request.getDeviceId())
                            .tanggalGabung(request.getTanggalGabung())
                            .hakCuti(request.getHakCuti())
                            .jenisKaryawan(request.getJenisKaryawan())
                            .build();

                            userRepository.save(updatedUser);

                            Map<String, Object> data = new HashMap<>();
                            data.put("user", updatedUser);
                
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", true);
                            response.put("message", "Update successful");
                            response.put("data", data);

                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } else {
                            Map<String, Object> response = new HashMap<>();
                            response.put("failed", true);
                            response.put("message", "Sorry, the new password and confirmation password don't match. Please make sure they match to proceed.");

                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        }
                    } else if (request.getNewPassword() == null || request.getConfirmPassword() == null || request.getOldPassword() == null) {
                        var updatedUser = UserEntity.builder()
                        .nik(request.getNik())
                        .namaKaryawan(request.getNamaKaryawan())
                        .email(request.getEmail())
                        .role(request.getRole())
                        .agama(request.getAgama())
                        .noKtp(request.getNoKtp())
                        .noNpwp(request.getNoNpwp())
                        .noTelepon(request.getNoTelepon())
                        .tempatLahir(request.getTempatLahir())
                        .tanggalLahir(request.getTanggalLahir())
                        .jenisKelamin(request.getJenisKelamin())
                        .golDarah(request.getGolDarah())
                        .statusNikah(request.getStatusNikah())
                        .jenjangPendidikan(request.getJenjangPendidikan())
                        .noRekening(request.getNoRekening())
                        .kewarganegaraan(request.getKewarganegaraan())
                        .alamatKtp(request.getAlamatKtp())
                        .alamatSekarang(request.getAlamatSekarang())
                        .kodePos(request.getKodePos())
                        .kontakDarurat(request.getKontakDarurat())
                        .statusKontak(request.getStatusKontak())
                        .alamatKontak(request.getAlamatKontak())
                        .teleponDarurat(request.getTeleponDarurat())
                        .deviceId(request.getDeviceId())
                        .tanggalGabung(request.getTanggalGabung())
                        .hakCuti(request.getHakCuti())
                        .jenisKaryawan(request.getJenisKaryawan())
                        .build();

                        userRepository.save(updatedUser);

                        Map<String, Object> data = new HashMap<>();
                        data.put("user", updatedUser);
            
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("message", "Update successful");
                        response.put("data", data);

                return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    Map<String, Object> response = new HashMap<>();
                    response.put("failed", true);
                    response.put("message", "Password doesn't match!");

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

    
              
            } 
    
            Map<String, Object> response = new HashMap<>();
            response.put("failed", true);
            response.put("message", "User not found!");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Update failed!");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}