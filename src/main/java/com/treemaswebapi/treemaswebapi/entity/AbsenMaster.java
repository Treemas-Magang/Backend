// package com.treemaswebapi.treemaswebapi.entity;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.SequenceGenerator;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Table;

// @Entity
// @Table
// public class AbsenMaster {
//     @Id
//     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absen_master_id_absen_seq")
//     @SequenceGenerator(name = "absen_master_id_absen_seq", sequenceName = "absen_master_id_absen_seq", allocationSize = 1)
//     @Column(name = "id_absen")
//     private int idAbsen;

//     @ManyToOne
//     @JoinColumn(name = "nik", referencedColumnName = "nik")
//     private UserEntity user;

//     @Column(name = "tanggal_absen")
//     private LocalDate tanggalAbsen;

//     @Column(name = "hari_absen")
//     private String hariAbsen;

//     @ManyToOne
//     @JoinColumn(name = "kode_project", referencedColumnName = "kode_project")
//     private ProjectMaster project;

//     @Column(name = "lokasi_masuk")
//     private String lokasiMasuk;

//     @Column(name = "jam_masuk")
//     private LocalTime jamMasuk;

//     @Column(name = "latitude_masuk")
//     private BigDecimal latitudeMasuk;

//     @Column(name = "longitude_masuk")
//     private BigDecimal longitudeMasuk;

//     @Column(name = "lokasi_pulang")
//     private String lokasiPulang;

//     @Column(name = "latitude_pulang")
//     private BigDecimal latitudePulang;

//     @Column(name = "longitude_pulang")
//     private BigDecimal longitudePulang;

//     @Column(name = "catatan_terlambat")
//     private String catatanTerlambat;

//     @Column(name = "total_jam_kerja")
//     private Short totalJamKerja;

//     @Column(name = "lembur")
//     private boolean lembur;

//     public int getIdAbsen() {
//         return idAbsen;
//     }

//     public void setIdAbsen(int idAbsen) {
//         this.idAbsen = idAbsen;
//     }

//     public UserEntity getUser() {
//         return user;
//     }

//     public void setUser(UserEntity user) {
//         this.user = user;
//     }

//     public LocalDate getTanggalAbsen() {
//         return tanggalAbsen;
//     }

//     public void setTanggalAbsen(LocalDate tanggalAbsen) {
//         this.tanggalAbsen = tanggalAbsen;
//     }

//     public String getHariAbsen() {
//         return hariAbsen;
//     }

//     public void setHariAbsen(String hariAbsen) {
//         this.hariAbsen = hariAbsen;
//     }

//     public ProjectMaster getProject() {
//         return project;
//     }

//     public void setProject(ProjectMaster project) {
//         this.project = project;
//     }

//     public String getLokasiMasuk() {
//         return lokasiMasuk;
//     }

//     public void setLokasiMasuk(String lokasiMasuk) {
//         this.lokasiMasuk = lokasiMasuk;
//     }

//     public LocalTime getJamMasuk() {
//         return jamMasuk;
//     }

//     public void setJamMasuk(LocalTime jamMasuk) {
//         this.jamMasuk = jamMasuk;
//     }

//     public BigDecimal getLatitudeMasuk() {
//         return latitudeMasuk;
//     }

//     public void setLatitudeMasuk(BigDecimal latitudeMasuk) {
//         this.latitudeMasuk = latitudeMasuk;
//     }

//     public BigDecimal getLongitudeMasuk() {
//         return longitudeMasuk;
//     }

//     public void setLongitudeMasuk(BigDecimal longitudeMasuk) {
//         this.longitudeMasuk = longitudeMasuk;
//     }

//     public String getLokasiPulang() {
//         return lokasiPulang;
//     }

//     public void setLokasiPulang(String lokasiPulang) {
//         this.lokasiPulang = lokasiPulang;
//     }

//     public BigDecimal getLatitudePulang() {
//         return latitudePulang;
//     }

//     public void setLatitudePulang(BigDecimal latitudePulang) {
//         this.latitudePulang = latitudePulang;
//     }

//     public BigDecimal getLongitudePulang() {
//         return longitudePulang;
//     }

//     public void setLongitudePulang(BigDecimal longitudePulang) {
//         this.longitudePulang = longitudePulang;
//     }

//     public String getCatatanTerlambat() {
//         return catatanTerlambat;
//     }

//     public void setCatatanTerlambat(String catatanTerlambat) {
//         this.catatanTerlambat = catatanTerlambat;
//     }

//     public Short getTotalJamKerja() {
//         return totalJamKerja;
//     }

//     public void setTotalJamKerja(Short totalJamKerja) {
//         this.totalJamKerja = totalJamKerja;
//     }

//     public boolean isLembur() {
//         return lembur;
//     }

//     public void setLembur(boolean lembur) {
//         this.lembur = lembur;
//     }

//     // Getters and setters
// }

