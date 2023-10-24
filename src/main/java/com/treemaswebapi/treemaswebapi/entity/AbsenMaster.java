package com.treemaswebapi.treemaswebapi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbsenMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absen_master_id_absen_seq")
    @SequenceGenerator(name = "absen_master_id_absen_seq", sequenceName = "absen_master_id_absen_seq", allocationSize = 1)
    @Column(name = "id_absen")
    private int idAbsen;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    private UserEntity user;

    @Column(name = "tanggal_absen")
    private LocalDate tanggalAbsen;

    @Column(name = "hari_absen")
    private String hariAbsen;

    @ManyToOne
    @JoinColumn(name = "kode_project", referencedColumnName = "kode_project")
    private ProjectMaster kodeProject;

    @Column(name = "lokasi_masuk")
    private String lokasiMasuk;

    @Column(name = "jam_masuk")
    private LocalTime jamMasuk;

    @Column(name = "latitude_masuk")
    private BigDecimal latitudeMasuk;

    @Column(name = "longitude_masuk")
    private BigDecimal longitudeMasuk;

    @Column(name = "lokasi_pulang")
    private String lokasiPulang;

    @Column(name = "latitude_pulang")
    private BigDecimal latitudePulang;

    @Column(name = "longitude_pulang")
    private BigDecimal longitudePulang;

    @Column(name = "catatan_terlambat")
    private String catatanTerlambat;

    @Column(name = "total_jam_kerja")
    private Short totalJamKerja;

    @Column(name = "lembur")
    private boolean lembur;

    @Column(name = "jarak")
    private int jarak;
}

