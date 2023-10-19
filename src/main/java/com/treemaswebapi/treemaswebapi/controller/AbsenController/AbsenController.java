// package com.treemaswebapi.treemaswebapi.controller.AbsenController;

// import java.util.List;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;
// import com.treemaswebapi.treemaswebapi.service.AbsenImageService.AbsenImageService;
// import com.treemaswebapi.treemaswebapi.service.AbsenService.AbsenService;

// import io.jsonwebtoken.io.IOException;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/absen")
// @RequiredArgsConstructor
// public class AbsenController {
// private final AbsenService project;
// private final AbsenImageService imageService;

//     @GetMapping("/project-list")
//     public ResponseEntity<List<ProjectMaster>> projectList(@RequestBody ProjectListRequest request) {
//         ResponseEntity<List<ProjectMaster>> response = project.projectList(request);
//         return response;    
//     }
    
//     @PostMapping("/absen-proof")
//     public ResponseEntity<String> uploadAbsenProof(@RequestParam("file") MultipartFile file) throws IOException{
//         try{
//             imageService.saveImage(file);
//             return ResponseEntity.ok("Image Uploaded!");
//         }catch (Exception e){
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
//         }
      
//     }
// }
