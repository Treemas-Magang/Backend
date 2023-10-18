package com.treemaswebapi.treemaswebapi.service.ProfileService;

import java.util.HashMap;
import java.util.Map;

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
            var user = userRepository.findByNik(request.getNik());

            // Kalau Usernya ditemukan di database
            if (user != null) {
                var updatedUser = UserEntity.builder()
                .nik(request.getNik())
                .namaKaryawan(request.getNamaKaryawan())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getNewPassword()))
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
