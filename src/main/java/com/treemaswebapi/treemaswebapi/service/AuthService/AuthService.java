package com.treemaswebapi.treemaswebapi.service.AuthService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.AuthController.ChangePasswordRequest;
import com.treemaswebapi.treemaswebapi.controller.AuthController.LoginRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.repository.KaryawanImageRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;

import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    public class AuthService {
        

        private final SysUserRepository sysUserRepository;
        private final KaryawanRepository karyawanRepository;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final KaryawanImageRepository karyawanImageRepository;

        public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
            try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getNik(), request.getPassword())
                );
                var user = sysUserRepository.findByUserId(request.getNik())
                    .orElseThrow();

                var userImg = karyawanImageRepository.findByNik(request.getNik());
                var karyawan = karyawanRepository.findByNik(request.getNik());
                // set Login true
                user.setIsLogin("1");
                String isWebAccess = request.getIsWebAccess();

                if ("0".equals(isWebAccess)) { // Check if it's not a web access

                    Optional<KaryawanEntity> dataKaryawan = karyawanRepository.findByNik(request.getNik());
        
                    if (dataKaryawan.isPresent()) {
                        KaryawanEntity karyawanEntity = dataKaryawan.get();
                        String existingHandsetImei = karyawanEntity.getHandsetImei();
                        String requestedHandsetImei = request.getHandsetImei();
        
                        if (existingHandsetImei == null) {
                            // If handsetImei in the database is null, set it from the request
                            karyawanEntity.setHandsetImei(requestedHandsetImei);
                            karyawanRepository.save(karyawanEntity);
                        } else if (!existingHandsetImei.equals(requestedHandsetImei)) {
                            // If handsetImei is already set and different from the request, send an unauthorized response
                            String message = "HANDSET IMEI MISMATCH!";
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", false);
                            response.put("data", message);
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                        }
                    }
                }

            var jwtToken = jwtService.generateToken(user);
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("nik", user.getUserId()); 
            userData.put("full_name", user.getFullName());
            userData.put("email", user.getEmail());
            userData.put("role", user.getRole().toString());
            userData.put("is_pass_chg", user.getIsPassChg());
            userData.put("karyawanImg", userImg.get().getFoto());
            userData.put("alamatKaryawan", karyawan.get().getAlamatSekarang());
            userData.put("jenisKelamin", karyawan.get().getJenisKelamin());

            Map<String, Object> data = new HashMap<>();
            data.put("user", userData);
            data.put("token", jwtToken);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("data", data);

            return ResponseEntity.status(HttpStatus.OK).body(response);

            } catch (Exception e) {
                
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Login failed!");
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        public ResponseEntity<Map<String, Object>> forgetPassword(
            ChangePasswordRequest request) {
            try {            
                Optional<SysUserEntity> existingEmail = sysUserRepository.findByEmail(request.getEmail());
                // Check jika email dari token sama dengan email dari request maka akan do something...
                if (existingEmail.isPresent()) {
                    Optional<SysUserEntity> sysUser = sysUserRepository.findByEmail(existingEmail.get().getEmail());
                    sysUser.get().setSqlPassword("123456");
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Set password to default success");
                    response.put("data", sysUser.get());

                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // TODO: handle exception
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "No Email found!");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } catch (Exception e) {
                // TODO: handle exception
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Set Password to default failed!");
                response.put("error", e.getMessage());

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }
    }
