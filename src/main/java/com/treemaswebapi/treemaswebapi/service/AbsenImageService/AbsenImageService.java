// package com.treemaswebapi.treemaswebapi.service.AbsenImageService;

// import org.springframework.web.multipart.MultipartFile;

// import com.treemaswebapi.treemaswebapi.entity.AbsenImageEntity;
// import com.treemaswebapi.treemaswebapi.repository.AbsenImageRepo;

// public class AbsenImageService {
//     private AbsenImageRepo absenImageRepo;

//     public String saveImage(MultipartFile file){
//     try {
//         if(!file.isEmpty())
//             {
//                 AbsenImageEntity image = new AbsenImageEntity();
//                 image.setImageData(file.getBytes());
//                 absenImageRepo.save(image);
//                 return "Image uploaded!";
//             } else {
//                 return "Image upload failed: The uploaded file is empty";
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "Image upload failed" + e.getMessage();
//         }
//     }
// }
// //nih tonton ini dulu baru bikin https://www.youtube.com/watch?v=oTJ89wcz5Ec