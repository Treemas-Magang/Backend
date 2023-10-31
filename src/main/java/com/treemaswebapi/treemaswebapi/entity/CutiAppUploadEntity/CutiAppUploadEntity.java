package com.treemaswebapi.treemaswebapi.entity.CutiAppUploadEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_cuti_app_upload", schema = "public")
public class CutiAppUploadEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "tgl_mulai")
    private Date tglMulai;

    @Column(name = "tgl_selesai")
    private Date tglSelesai;

    @Column(name = "tgl_kembali_kerja")
    private Date tglKembaliKerja;

    @Column(name = "jml_cuti")
    private BigDecimal jmlCuti;

    @Column(name = "keperluan_cuti")
    private String keperluanCuti;

    @Column(name = "alamat_cuti")
    private String alamatCuti;

    @Column(name = "flg_ket", length = 50)
    private String flgKet;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrapp", length = 50)
    private String usrapp;

    @Column(name = "dtmapp")
    private Timestamp dtmapp;

    @Column(name = "flag_app", length = 50)
    private String flagApp;

    @Column(name = "note_app")
    private String noteApp;

    @Column(name = "is_approved", length = 1)
    private String isApproved;

    @Column(name = "jml_cuti_bersama")
    private BigDecimal jmlCutiBersama;

    @Column(name = "jml_cuti_khusus")
    private BigDecimal jmlCutiKhusus;
}