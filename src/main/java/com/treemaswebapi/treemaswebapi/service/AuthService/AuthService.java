    package com.treemaswebapi.treemaswebapi.service.AuthService;

    import java.util.HashMap;
    import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.stereotype.Service;

    import com.treemaswebapi.treemaswebapi.config.JwtService;
    import com.treemaswebapi.treemaswebapi.controller.AuthController.LoginRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
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

        public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
            try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getNik(), request.getPassword())
                );
                var user = sysUserRepository.findByUserId(request.getNik())
                    .orElseThrow();
        
                // set Login true
                user.setIsLogin("1");
        
                Optional<KaryawanEntity> isHandsetImeiOptional = karyawanRepository.findByNik(request.getNik());
        
                if (isHandsetImeiOptional.isPresent()) {
                    KaryawanEntity karyawanEntity = isHandsetImeiOptional.get();
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
        
                var jwtToken = jwtService.generateToken(user);
        
                Map<String, Object> userData = new HashMap<>();
                userData.put("nik", user.getUserId());
                userData.put("namaKaryawan", user.getFullName());
                userData.put("email", user.getEmail());
                userData.put("role", user.getRole().toString());
        
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
        
    }
