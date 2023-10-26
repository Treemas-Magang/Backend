package com.treemaswebapi.treemaswebapi.entity;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_karyawan_image", schema = "public")
public class KaryawanImageEntity {

    @Id
    @Column(name = "nik")
    private String nik;

    @Column(name = "foto_ktp")
    private String fotoKtp;

    @Column(name = "foto_ktp_path")
    private String fotoKtpPath;

    @Column(name = "foto_npwp")
    private String fotoNpwp;

    @Column(name = "foto_npwp_path")
    private String fotoNpwpPath;

    @Column(name = "foto_kk")
    private String fotoKk;

    @Column(name = "foto_kk_path")
    private String fotoKkPath;

    @Column(name = "foto_asuransi")
    private String fotoAsuransi;

    @Column(name = "foto_asuransi_path")
    private String fotoAsuransiPath;

    @Column(name = "foto_path")
    private String fotoPath;

    @Column(name = "foto")
    private String foto;

    public KaryawanImageEntity(
        MultipartFile foto,
        MultipartFile fotoKtp,
        MultipartFile fotoNpwp,
        MultipartFile fotoKk,
        MultipartFile fotoAsuransi
    ) {
        this.foto = convertToBase64(foto);
        this.fotoKtp = convertToBase64(fotoKtp);
        this.fotoNpwp = convertToBase64(fotoNpwp);
        this.fotoKk = convertToBase64(fotoKk);
        this.fotoAsuransi = convertToBase64(fotoAsuransi);
    }

    // Helper method to convert MultipartFile to Base64
    private String convertToBase64(MultipartFile file) {
        try {
            if (file != null) {
                byte[] bytes = file.getBytes();
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (IOException e) {
            // Handle the exception, for example, log it
            e.printStackTrace();
        }
        return null; // or an empty string if needed
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "nik")
    private KaryawanEntity karyawan;
}
