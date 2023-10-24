package com.treemaswebapi.treemaswebapi.service.AbsenImageService;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.treemaswebapi.treemaswebapi.entity.AbsenImageEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenImageRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AbsenImageService {
    @Autowired
    private AbsenImageRepo absenImageRepo;

    public ResponseEntity<Map<String, String>> uploadImage(MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                AbsenImageEntity image = new AbsenImageEntity();
                // Convert the image to bytes
                byte[] imageData = file.getBytes();
                image.setImageData(imageData);
                absenImageRepo.save(image);
                return ResponseEntity.ok().body(Map.of("message", "Image uploaded!"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Image upload failed: The uploaded file is empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Image upload failed: " + e.getMessage()));
        }
    }
    
    public ResponseEntity<byte[]> retrieveImage(int imageId) {
        try {
            Optional<AbsenImageEntity> imageOptional = absenImageRepo.findById(imageId);
            if (imageOptional.isPresent()) {
                AbsenImageEntity image = imageOptional.get();
                // You may want to add more error handling for cases where image data is not available.
                return ResponseEntity.ok().body(image.getImageData());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new byte[0]); // Return empty byte array or handle the error as needed.
        }
    }

    public ResponseEntity<Map<String, String>> updateImage(int imageId, MultipartFile file) {
        try {
            Optional<AbsenImageEntity> imageOptional = absenImageRepo.findById(imageId);
            if (imageOptional.isPresent()) {
                AbsenImageEntity image = imageOptional.get();
                if (file != null && !file.isEmpty()) {
                    byte[] imageData = file.getBytes();
                    image.setImageData(imageData);
                    absenImageRepo.save(image);
                    return ResponseEntity.ok().body(Map.of("message", "Image updated!"));
                } else {
                    return ResponseEntity.badRequest().body(Map.of("error", "Image update failed: The uploaded file is empty"));
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Image update failed: " + e.getMessage()));
        }
    }

    public ResponseEntity<Map<String, String>> deleteImage(int imageId) {
        try {
            Optional<AbsenImageEntity> imageOptional = absenImageRepo.findById(imageId);
            if (imageOptional.isPresent()) {
                absenImageRepo.deleteById(imageId);
                return ResponseEntity.ok().body(Map.of("message", "Image deleted!"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Image deletion failed: " + e.getMessage()));
        }
    }
    
    
    // Implement updateImage, deleteImage, and retrieveImage functions here
}
