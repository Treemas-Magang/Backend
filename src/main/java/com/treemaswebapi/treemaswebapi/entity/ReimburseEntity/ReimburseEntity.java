package com.treemaswebapi.treemaswebapi.entity.ReimburseEntity; // Replace with your actual package name

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_reimburse", schema = "public")
public class ReimburseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "hari", length = 50)
    private String hari;

    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "project_id", length = 20)
    private String projectId;

    @Column(name = "jam_masuk")
    private Time jamMasuk;

    @Column(name = "jam_keluar")
    private Time jamKeluar;

    @Column(name = "total_jam_kerja", precision = 18, scale = 0)
    private BigDecimal totalJamKerja;

    @Column(name = "transport", precision = 18, scale = 0)
    private BigDecimal transport;

    @Column(name = "uang_makan", precision = 18, scale = 0)
    private BigDecimal uangMakan;

    @Column(name = "flg_ket", length = 50)
    private String flgKet;

    @Column(name = "usrcrt")
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrapp")
    private String usrapp;

    @Column(name = "dtmapp")
    private Timestamp dtmapp;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "note_app")
    private String noteApp;
}
