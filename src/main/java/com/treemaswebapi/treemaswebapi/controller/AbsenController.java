// package com.treemaswebapi.treemaswebapi.controller;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.treemaswebapi.treemaswebapi.entity.AbsenMaster;
// import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
// import com.treemaswebapi.treemaswebapi.entity.UserEntity;
// import com.treemaswebapi.treemaswebapi.repository.AbsenMasterRepo;
// import com.treemaswebapi.treemaswebapi.repository.ListMemberRepo;
// import com.treemaswebapi.treemaswebapi.repository.ProjectMasterRepo;
// import com.treemaswebapi.treemaswebapi.repository.UserRepository;
// import com.treemaswebapi.treemaswebapi.service.JwtService;
// import com.treemaswebapi.treemaswebapi.service.UserService;

// import io.jsonwebtoken.Claims;
// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// @RequestMapping("/absen")
// public class AbsenController {
//     @Autowired
//     private HttpServletRequest request;

//     @Autowired
//     private JwtService jwtService;
//     AbsenMasterRepo absenMasterRepo;
//     ListMemberRepo listMemberRepo;
//     UserRepository userRepository;
//     AbsenMaster absenMaster;
//     ProjectMasterRepo projectMasterRepo;
//     ProjectMaster projectMaster;

//     private Map<String, String> prepareAbsenData(String namaProject, String alamatProject) {
//         Map<String, String> data = new HashMap<>();
//         data.put("namaProject", namaProject);
//         data.put("alamatProject", alamatProject);
//         // Add more data as needed
//         return data;
//     }

//     @GetMapping("/project-list")
//     public ResponseEntity<ApiResponse> absen(@RequestHeader("Authorization") String token) {
//         // Validate the token
//         Claims claims = jwtService.validateTokenAndGetClaims(token);
//         if (claims == null) {
//     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid token", null, 0));
//     }
//         // Extract user information from the token (e.g., nik)
        
//         String nik = claims.get("nik", String.class);

//         UserEntity userEntity = userRepository.findByNik(nik);

//         int idAbsen = absenMaster.getIdAbsen();

//         AbsenMaster absenMaster = absenMasterRepo.findByIdAbsen(idAbsen);

//         String kodeProject = projectMaster.getKodeProject();

        

//         ApiResponse response;

//         if (userEntity == null) {
//             response = new ApiResponse(false, "User not found", null, 0);
//         } else {
//             // Check if the user is in listmember_project
//             if (listMemberRepo.isUserInListMemberProject(nik)) {
//                 // User is in listmember_project
//                 String namaProject = projectMasterRepo.findNamaProjectByKodeProject(kodeProject);
//                 String alamatProject = projectMasterRepo.findAlamatProjectByKodeProject(kodeProject);
//                 response = new ApiResponse(true, "User is in listmember_project", prepareAbsenData(namaProject, alamatProject), 1);
//             } else {
//                 // User is not in listmember_project
//                 response = new ApiResponse(false, "Anda belum terdaftar pada project manapun, silakan hubungi atasan", null, 0);
//             }
//         }

//         return ResponseEntity.ok(response);
//     }

//     // Implement helper methods for checking listmember_project, searching kode_project, and retrieving project data
// }
