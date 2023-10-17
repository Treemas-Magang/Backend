package com.treemaswebapi.treemaswebapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AuthController.AuthenticationResponse;
import com.treemaswebapi.treemaswebapi.controller.AuthController.LoginRequest;
import com.treemaswebapi.treemaswebapi.controller.AuthController.RegisterRequest;
import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;
import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
            .nik(request.getNik())
            .namaKaryawan(request.getNamaKaryawan())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .agama(request.getAgama()) // Chain property assignments like this
            .noKtp(request.getNoKtp()) // Add more property assignments in a similar way
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
    
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);       
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build(); 
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getNik(), request.getPassword())
        );
        var user = userRepository.findByNik(request.getNik())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);       
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();    
        }
}
