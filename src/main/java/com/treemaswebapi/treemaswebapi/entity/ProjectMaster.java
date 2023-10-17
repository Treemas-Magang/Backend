// package com.treemaswebapi.treemaswebapi.entity;

// import java.math.BigDecimal;
// import java.time.LocalTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "projectmaster", schema = "public")
// public class ProjectMaster {
//     @Id
//     @Column(name = "kode_project")
//     private String kodeProject;

//     @Column(name = "nama_project")
//     private String namaProject;

//     @Column(name = "no_telpon")
//     private String noTelpon;

//     @Column(name = "kota")
//     private String kota;

//     @Column(name = "alamat_project")
//     private String alamatProject;

//     @Column(name = "latitude_project")
//     private BigDecimal latitudeProject;

//     @Column(name = "longitude_project")
//     private BigDecimal longitudeProject;

//     @Column(name = "reimburse_project")
//     private BigDecimal reimburseProject;

//     @Column(name = "jarak_maksimal")
//     private BigDecimal jarakMaksimal;

//     @Column(name = "total_jam_kerja")
//     private Short totalJamKerja;

//     @Column(name = "jam_masuk")
//     private LocalTime jamMasuk;

//     @Column(name = "jam_keluar")
//     private LocalTime jamKeluar;

//     public String getKodeProject() {
//         return kodeProject;
//     }

//     public void setKodeProject(String kodeProject) {
//         this.kodeProject = kodeProject;
//     }

//     public String getNamaProject() {
//         return namaProject;
//     }

//     public void setNamaProject(String namaProject) {
//         this.namaProject = namaProject;
//     }

//     public String getNoTelpon() {
//         return noTelpon;
//     }

//     public void setNoTelpon(String noTelpon) {
//         this.noTelpon = noTelpon;
//     }

//     public String getKota() {
//         return kota;
//     }

//     public void setKota(String kota) {
//         this.kota = kota;
//     }

//     public String getAlamatProject() {
//         return alamatProject;
//     }

//     public void setAlamatProject(String alamatProject) {
//         this.alamatProject = alamatProject;
//     }

//     public BigDecimal getLatitudeProject() {
//         return latitudeProject;
//     }

//     public void setLatitudeProject(BigDecimal latitudeProject) {
//         this.latitudeProject = latitudeProject;
//     }

//     public BigDecimal getLongitudeProject() {
//         return longitudeProject;
//     }

//     public void setLongitudeProject(BigDecimal longitudeProject) {
//         this.longitudeProject = longitudeProject;
//     }

//     public BigDecimal getReimburseProject() {
//         return reimburseProject;
//     }

//     public void setReimburseProject(BigDecimal reimburseProject) {
//         this.reimburseProject = reimburseProject;
//     }

//     public BigDecimal getJarakMaksimal() {
//         return jarakMaksimal;
//     }

//     public void setJarakMaksimal(BigDecimal jarakMaksimal) {
//         this.jarakMaksimal = jarakMaksimal;
//     }

//     public Short getTotalJamKerja() {
//         return totalJamKerja;
//     }

//     public void setTotalJamKerja(Short totalJamKerja) {
//         this.totalJamKerja = totalJamKerja;
//     }

//     public LocalTime getJamMasuk() {
//         return jamMasuk;
//     }

//     public void setJamMasuk(LocalTime jamMasuk) {
//         this.jamMasuk = jamMasuk;
//     }

//     public LocalTime getJamKeluar() {
//         return jamKeluar;
//     }

//     public void setJamKeluar(LocalTime jamKeluar) {
//         this.jamKeluar = jamKeluar;
//     }

//     // Getters and setters
// }
