package com.treemaswebapi.treemaswebapi.entity.TimesheetEntity;

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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_timesheet", schema = "public")
public class TimesheetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "hari", length = 50)
    private String hari;

    @Column(name = "tgl_msk")
    private Date tglMsk;

    @Column(name = "jam_masuk")
    private Time jamMasuk;

    @Column(name = "jam_keluar")
    private Time jamKeluar;

    @Column(name = "overtime", precision = 18, scale = 0)
    private BigDecimal overtime;

    @Column(name = "total_jam_kerja", precision = 18, scale = 0)
    private BigDecimal totalJamKerja;

    @Column(name = "project_id", length = 20)
    private String projectId;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "flg_ket", length = 50)
    private String flgKet;

    @Column(name = "usrcrt")
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;
}
