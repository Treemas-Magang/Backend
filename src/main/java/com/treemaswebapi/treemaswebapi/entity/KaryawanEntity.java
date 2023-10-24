package com.treemaswebapi.treemaswebapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date; // Import java.util.Date untuk mendukung tanggal lahir

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_karyawan", schema = "public")
public class KaryawanEntity {

    @Id
    @Column(name = "nik", length = 15)
    @NotBlank(message = "NIK tidak boleh kosong")
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;

    @Column(name = "jenis_kelamin", length = 50)
    private String jenisKelamin;

    @Column(name = "agama", length = 50)
    private String agama;

    @Column(name = "kewarganegaraan", length = 50)
    private String kewarganegaraan;

    @Column(name = "kode_pos", length = 50)
    private String kodePos;

    @Column(name = "alamat_ktp")
    private String alamatKtp;

    @Column(name = "no_hp", length = 40)
    private String noHp;

    @Email
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "no_rek", length = 50)
    private String noRek;

    @Column(name = "no_npwp", length = 50)
    private String noNpwp;

    @Column(name = "jenjang_pendidikan", length = 50)
    private String jenjangPendidikan;

    @Column(name = "tanggal_bergabung")
    private Date tanggalBergabung;

    @Column(name = "alamat_sekarang")
    private String alamatSekarang;

    @Column(name = "status_perkawinan", length = 50)
    private String statusPerkawinan;

    @Column(name = "golongan_darah", length = 50)
    private String golonganDarah;

    @Column(name = "nomor_ktp", length = 50)
    private String nomorKtp;

    @Column(name = "emergency_contact", length = 50)
    private String emergencyContact;

    @Column(name = "status_emergency", length = 50)
    private String statusEmergency;

    @Column(name = "alamat_emergency")
    private String alamatEmergency;

    @Column(name = "telp_emergency", length = 50)
    private String telpEmergency;

    @Column(name = "project_id", length = 20)
    private String projectId;

    @Column(name = "divisi", length = 50)
    private String divisi;

    @Column(name = "nik_leader", length = 100)
    private String nikLeader;

    @Column(name = "is_leader", length = 1)
    private String isLeader;

    @Column(name = "usrupd")
    private String usrUpd;

    @Column(name = "dtmupd")
    private Timestamp dtmUpd;

    @Column(name = "handset_imei", length = 100)
    private String handsetImei;

    @Column(name = "hak_cuti")
    private BigDecimal  hakCuti;

    @Column(name = "is_karyawan", length = 1)
    private String isKaryawan;

}
