package com.treemaswebapi.treemaswebapi.entity.ProjectEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_project", schema = "public")
public class ProjectEntity implements Serializable {

    @Id
    @Column(name = "project_id", length = 20, nullable = false)
    private String projectId;

    @Column(name = "nama_project", length = 50)
    private String namaProject;

    @Column(name = "lokasi", length = 255)
    private String lokasi;

    @Column(name = "jam_kerja", precision = 18, scale = 0)
    private BigDecimal jamKerja;

    @Column(name = "jam_masuk")
    private Time jamMasuk;

    @Column(name = "jam_keluar")
    private Time jamKeluar;

    @Column(name = "no_tlpn", length = 64)
    private String noTlpn;

    @Column(name = "biaya_reimburse")
    private Double biayaReimburse;

    @Column(name = "gps_latitude")
    private Double gpsLatitude;

    @Column(name = "gps_longitude")
    private Double gpsLongitude;

    @Column(name = "kota", length = 150)
    private String kota;

    @Column(name = "jrk_max", length = 10)
    private String jrkMax;

    @Column(name = "usrupd")
    private String usrupd;

    @Column(name = "dtmupd")
    private Timestamp dtmupd;
}
