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

        public ResponseEntity<Map<String, String>> register(RegisterRequest request) {
            try {
            var user = UserEntity.builder()
                .nik(request.getNik())
                .namaKaryawan(request.getNamaKaryawan())
                .email(request.getEmail())
                .password(passwordEncoder.encode("123456"))
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

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Registration successful");

            return ResponseEntity.status(HttpStatus.OK).body(response);
            
            } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
            try {

            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNik(), request.getPassword())
            );
            var user = userRepository.findByNik(request.getNik())
                .orElseThrow();

            // Di login process check device id sesuai request dari nik tertentu dan compare ke database, kalo null kita set deviceId nya.
            if(user.getDeviceId() == null) {
                user.setDeviceId(request.getDeviceId());
                userRepository.save(user);
            }

            var jwtToken = jwtService.generateToken(user);
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("nik", user.getNik()); 
            userData.put("namaKaryawan", user.getNamaKaryawan());
            userData.put("email", user.getEmail());
            userData.put("role", user.getRole().toString());
            userData.put("deviceId", user.getDeviceId());

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
