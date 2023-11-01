package com.treemaswebapi.treemaswebapi.entity.TimesheetEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

// project_id FK dari tbl_project

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_timesheet", schema = "public")
public class TimesheetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik")
    private String nik;

    @Column(name = "nama")
    private String nama;

    @Column(name = "hari")
    private String hari;

    @Column(name = "tgl_msk")
    private Date tglMsk;

    @Column(name = "jam_masuk")
    private Time jamMasuk;

    @Column(name = "jam_keluar")
    private Time jamKeluar;

    @Column(name = "overtime")
    private BigDecimal overtime;

    @Column(name = "total_jam_kerja")
    private BigDecimal totalJamKerja;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "note")
    private String note;

    @Column(name = "flg_ket")
    private String flgKet;

    @Column(name = "usrcrt")
    private String usrCrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmCrt;
}
