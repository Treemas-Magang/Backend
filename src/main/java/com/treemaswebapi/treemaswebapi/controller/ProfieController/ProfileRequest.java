package com.treemaswebapi.treemaswebapi.controller.ProfieController;

import java.util.Date;

import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private String nik;
    private String namaKaryawan;
    private String noKtp;
    private String noNpwp;
    private String noTelepon;
    private String email;
    private String tempatLahir;
    private Date tanggalLahir;
    private String jenisKelamin;
    private String golDarah;
    private String statusNikah;
    private String agama;
    private String jenjangPendidikan;
    private String noRekening;
    private String kewarganegaraan;
    private String alamatKtp;
    private String alamatSekarang;
    private String kodePos;
    private String kontakDarurat;
    private String statusKontak;
    private String alamatKontak;
    private String teleponDarurat;
    private String deviceId;
    private Date tanggalGabung;
    private Integer hakCuti;
    private String jenisKaryawan;
    private Role role;
    
    // Password Change
    private String oldPassword;
    private String newPassword;
    private String confirmPassword; 
}
