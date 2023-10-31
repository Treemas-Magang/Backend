    package com.treemaswebapi.treemaswebapi.service.AuthService;

    import java.util.HashMap;
    import java.util.Map;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import com.treemaswebapi.treemaswebapi.config.JwtService;
    import com.treemaswebapi.treemaswebapi.controller.AuthController.LoginRequest;
    import com.treemaswebapi.treemaswebapi.controller.AuthController.RegisterRequest;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;
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
            var user = sysUserRepository.findByUserid(request.getNik())
                .orElseThrow();

            var isHandsetImei = karyawanRepository.findByNik(request.getNik());

            // Di login process check device id sesuai request dari nik tertentu dan compare ke database,
            // kalo null kita set deviceId nya.
            if(isHandsetImei.getHandsetImei() == null) {
                isHandsetImei.setHandsetImei(request.getHandsetImei());
                karyawanRepository.save(isHandsetImei);
            }

            var jwtToken = jwtService.generateToken(user);
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("nik", user.getUserid()); 
            userData.put("namaKaryawan", user.getFullName());
            userData.put("email", user.getEmail());
            userData.put("role", user.getRole().toString());
            userData.put("handset imei", isHandsetImei.getHandsetImei());

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
