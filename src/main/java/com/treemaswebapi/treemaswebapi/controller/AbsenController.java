package com.treemaswebapi.treemaswebapi.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.treemaswebapi.treemaswebapi.entity.AbsenImageEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenMaster;
import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
import com.treemaswebapi.treemaswebapi.entity.UserEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenImageRepo;
import com.treemaswebapi.treemaswebapi.repository.AbsenMasterRepo;
import com.treemaswebapi.treemaswebapi.repository.ListMemberRepo;
import com.treemaswebapi.treemaswebapi.repository.ProjectMasterRepo;
import com.treemaswebapi.treemaswebapi.repository.UserRepository;
import com.treemaswebapi.treemaswebapi.request.AbsenImageRequest;
import com.treemaswebapi.treemaswebapi.response.AbsenApiResponse;
import com.treemaswebapi.treemaswebapi.response.LoginApiResponse;
import com.treemaswebapi.treemaswebapi.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/absen")
public class AbsenController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtService jwtService;
    AbsenMasterRepo absenMasterRepo;
    ListMemberRepo listMemberRepo;
    UserRepository userRepository;
    AbsenImageRepo absenImageRepo;
    AbsenMaster absenMaster;
    ProjectMasterRepo projectMasterRepo;
    ProjectMaster projectMaster;

    private Map<String, String> prepareAbsenData(String namaProject, String alamatProject) {
        Map<String, String> data = new HashMap<>();
        data.put("namaProject", namaProject);
        data.put("alamatProject", alamatProject);
        return data;
    }

    @GetMapping("/project-list")
    public ResponseEntity<AbsenApiResponse> absen(@RequestHeader("Authorization") String token) {
        // Validate the token
        Claims claims = jwtService.decodeToken(token);
        if (claims == null) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AbsenApiResponse(false, "Invalid token", null, 0));
    }
        // Extract user information from the token (e.g., nik)
        
        String nik = claims.getId();

        UserEntity userEntity = userRepository.findByNik(nik);

        // int idAbsen = absenMaster.getIdAbsen();

        // AbsenMaster absenMaster = absenMasterRepo.findByIdAbsen(idAbsen);

        String kodeProject = projectMaster.getKodeProject();

        AbsenApiResponse response;

        if (userEntity == null) {
            response = new AbsenApiResponse(false, "User not found", null, 0);
        } else {
            // Check if the user is in listmember_project
            if (listMemberRepo.isUserInListMemberProject(nik)) {
                // User is in listmember_project
                String namaProject = projectMasterRepo.findNamaProjectByKodeProject(kodeProject);
                String alamatProject = projectMasterRepo.findAlamatProjectByKodeProject(kodeProject);
                response = new AbsenApiResponse(true, "User is in listmember_project", prepareAbsenData(namaProject, alamatProject), 1);
            } else {
                // User is not in listmember_project
                response = new AbsenApiResponse(false, "Anda belum terdaftar pada project manapun, silakan hubungi atasan", null, 0);
            }
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/absen-proof")
    public ResponseEntity<LoginApiResponse> uploadAbsen(@RequestBody AbsenImageRequest imageRequest) {
        try {
            // Access image data from imageUploadDTO
            String nik = imageRequest.getNik();
            byte[] imageBytes = imageRequest.getImage();
    
            // Create a AbsenImageEntity and set the "nik" and image
            AbsenImageEntity absenImageEntity = new AbsenImageEntity();
            absenImageEntity.setIdAbsenImg(+1);
            absenImageEntity.setImage(imageBytes);// Set the nik from DTO
    
            // Save the entity to the database using the JpaRepository
            absenImageRepo.save(absenImageEntity);
    
            // Return a success response
            return ResponseEntity.ok(new LoginApiResponse(true, "Image uploaded successfully", null, null, 1));
        } catch (Exception e) {
            // Handle exceptions, e.g., if there's an issue with file upload
            return ResponseEntity.badRequest().body(new LoginApiResponse(false, "Error uploading image.", null, null, 0));
        }
    }
    // public ResponseEntity<ApiResponse> uploadAbsen(
    //     @RequestBody("file") MultipartFile file,
    //     @RequestParam String nik
    //     ){
    //         try {
    //             // Convert the MultipartFile to a byte array
    //             byte[] imageBytes = file.getBytes();
        
    //             // Create a UserEntity and set the "nik" and image
    //             AbsenImageEntity absenImageEntity = new AbsenImageEntity();
    //             absenImageEntity.setIdAbsenImg(+1);
    //             absenImageEntity.setImage(imageBytes);
        
    //             // Save the entity to the database using the JpaRepository
    //             absenImageRepo.save(absenImageEntity);
        
    //             // Return a success response
    //             return ResponseEntity.ok(new ApiResponse(true, "Image uploaded successfully", null, null, 1));
    //         } catch (IOException e) {
    //             // Handle exceptions, e.g., if there's an issue with file upload
    //             return ResponseEntity.badRequest().body(new ApiResponse(false, "Error uploading image.",null, null, 0));
    //         }
    //     }
    
}