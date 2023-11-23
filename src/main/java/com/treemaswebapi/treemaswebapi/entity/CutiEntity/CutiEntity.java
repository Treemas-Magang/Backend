package com.treemaswebapi.treemaswebapi.entity.CutiEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Entity
@Table(name = "tbl_cuti", schema = "public")
public class CutiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik")
    private String nik;

    @Column(name = "nama")
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

    @Column(name = "flg_ket")
    private String flgKet;

    @Column(name = "usrcrt")
    private String usrCrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmCrt;

    @Column(name = "usrapp")
    private String usrApp;

    @Column(name = "dtmapp")
    private Timestamp dtmApp;

    @Column(name = "flag_app")
    private String flagApp;

    @Column(name = "note_app")
    private String noteApp;

    @Column(name = "is_approved")
    private String isApproved;

    @Column(name = "jml_cuti_bersama")
    private BigDecimal jmlCutiBersama;

    @Column(name = "jml_cuti_khusus")
    private BigDecimal jmlCutiKhusus;

    @Column(name = "sisa_cuti")
    private BigDecimal sisaCuti;

    @OneToOne
    @JoinColumn(name = "jenis_cuti", referencedColumnName = "id")
    private MasterCutiEntity masterCutiEntity;

}