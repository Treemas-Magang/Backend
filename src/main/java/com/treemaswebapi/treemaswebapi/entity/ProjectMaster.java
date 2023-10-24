package com.treemaswebapi.treemaswebapi.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projectmaster", schema = "public")
public class ProjectMaster {
    @Id
    @Column(name = "kode_project")
    private String kodeProject;

    @Column(name = "nama_project")
    private String namaProject;

    @Column(name = "no_telpon")
    private String noTelpon;

    @Column(name = "kota")
    private String kota;

    @Column(name = "alamat_project")
    private String alamatProject;

    @Column(name = "latitude_project")
    private BigDecimal latitudeProject;

    @Column(name = "longitude_project")
    private BigDecimal longitudeProject;

    @Column(name = "reimburse_project")
    private BigDecimal reimburseProject;

    @Column(name = "jarak_maksimal")
    private BigDecimal jarakMaksimal;

    @Column(name = "total_jam_kerja")
    private Short totalJamKerja;

    @Column(name = "jam_masuk")
    private LocalTime jamMasuk;

    @Column(name = "jam_keluar")
    private LocalTime jamKeluar;

}
