package com.treemaswebapi.treemaswebapi.entity.CancelCutiEntity;

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
@Table(name = "tbl_cancel_cuti", schema = "public")
public class CancelCutiEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", length = 20)
    private String projectId;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "hari", length = 50)
    private String hari;

    @Column(name = "tgl_absen")
    private Date tglAbsen;

    @Column(name = "gps_latitude_msk")
    private Double gpsLatitudeMsk;

    @Column(name = "gps_longitude_msk")
    private Double gpsLongitudeMsk;

    @Column(name = "lokasi_msk", length = 255)
    private String lokasiMsk;

    @Column(name = "jarak_msk", length = 53)
    private String jarakMsk;

    @Column(name = "jam_msk")
    private Time jamMsk;

    @Column(name = "gps_latitude_plg")
    private Double gpsLatitudePlg;

    @Column(name = "gps_longitude_plg")
    private Double gpsLongitudePlg;

    @Column(name = "lokasi_plg", length = 255)
    private String lokasiPlg;

    @Column(name = "jarak_plg", length = 53)
    private String jarakPlg;

    @Column(name = "jam_plg")
    private Time jamPlg;

    @Column(name = "note_pekerjaan", length = 255)
    private String notePekerjaan;

    @Column(name = "note_telat_msk", length = 255)
    private String noteTelatMsk;

    @Column(name = "note_plg_cepat", length = 255)
    private String notePlgCepat;

    @Column(name = "note_other", length = 255)
    private String noteOther;

    @Column(name = "total_jam_kerja", precision = 18, scale = 0)
    private BigDecimal totalJamKerja;

    @Column(name = "is_lembur", length = 1)
    private String isLembur;

    @Column(name = "is_absen", length = 1)
    private String isAbsen;

    @Column(name = "is_sakit", length = 1)
    private String isSakit;

    @Column(name = "is_cuti", length = 1)
    private String isCuti;

    @Column(name = "is_libur", length = 1)
    private String isLibur;

    @Column(name = "is_other", length = 1)
    private String isOther;

    @Column(name = "is_wfh", length = 1)
    private String isWFH;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrapp", length = 50)
    private String usrapp;

    @Column(name = "dtmapp")
    private Timestamp dtmapp;

    @Column(name = "note_app", length = 255)
    private String noteApp;
}
