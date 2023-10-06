package com.treemaswebapi.treemaswebapi.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "absenmaster", schema = "public")
public class AbsenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_absen")
    private int idAbsen;

    @Column(name = "nik", length = 50)
    private String nik;

    @Column(name = "tanggal_absen")
    private Date tanggalAbsen;

    @Column(name = "hari_absen", length = 6)
    private String hariAbsen;

    @Column(name = "kode_project", length = 10)
    private String kodeProject;

    @Column(name = "lokasi_masuk", columnDefinition = "text")
    private String lokasiMasuk;

    @Column(name = "jam_masuk")
    private Time jamMasuk;

    @Column(name = "latitude_masuk")
    private BigDecimal latitudeMasuk;

    @Column(name = "longitude_masuk")
    private BigDecimal longitudeMasuk;

    @Column(name = "lokasi_pulang", columnDefinition = "text")
    private String lokasiPulang;

    @Column(name = "jam_pulang")
    private Time jamPulang;

    @Column(name = "latitude_pulang")
    private BigDecimal latitudePulang;

    @Column(name = "longitude_pulang")
    private BigDecimal longitudePulang;

    @Column(name = "catatan_terlambat", columnDefinition = "text")
    private String catatanTerlambat;

    @Column(name = "total_jam_kerja")
    private Integer totalJamKerja;

    @Column(name = "lembur")
    private Integer lembur;

    // Constructors

    public AbsenEntity() {
    }

    public AbsenEntity(String nik, Date tanggalAbsen, String hariAbsen, String kodeProject,
                       String lokasiMasuk, Time jamMasuk, BigDecimal latitudeMasuk, BigDecimal longitudeMasuk,
                       String lokasiPulang, Time jamPulang, BigDecimal latitudePulang, BigDecimal longitudePulang,
                       String catatanTerlambat, Integer totalJamKerja, Integer lembur) {
        this.nik = nik;
        this.tanggalAbsen = tanggalAbsen;
        this.hariAbsen = hariAbsen;
        this.kodeProject = kodeProject;
        this.lokasiMasuk = lokasiMasuk;
        this.jamMasuk = jamMasuk;
        this.latitudeMasuk = latitudeMasuk;
        this.longitudeMasuk = longitudeMasuk;
        this.lokasiPulang = lokasiPulang;
        this.jamPulang = jamPulang;
        this.latitudePulang = latitudePulang;
        this.longitudePulang = longitudePulang;
        this.catatanTerlambat = catatanTerlambat;
        this.totalJamKerja = totalJamKerja;
        this.lembur = lembur;
    }

    // Getter and setter methods

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Date getTanggalAbsen() {
        return tanggalAbsen;
    }

    public void setTanggalAbsen(Date tanggalAbsen) {
        this.tanggalAbsen = tanggalAbsen;
    }

    public String getHariAbsen() {
        return hariAbsen;
    }

    public void setHariAbsen(String hariAbsen) {
        this.hariAbsen = hariAbsen;
    }

    public String getKodeProject() {
        return kodeProject;
    }

    public void setKodeProject(String kodeProject) {
        this.kodeProject = kodeProject;
    }

    public String getLokasiMasuk() {
        return lokasiMasuk;
    }

    public void setLokasiMasuk(String lokasiMasuk) {
        this.lokasiMasuk = lokasiMasuk;
    }

    public Time getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(Time jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public BigDecimal getLatitudeMasuk() {
        return latitudeMasuk;
    }

    public void setLatitudeMasuk(BigDecimal latitudeMasuk) {
        this.latitudeMasuk = latitudeMasuk;
    }

    public BigDecimal getLongitudeMasuk() {
        return longitudeMasuk;
    }

    public void setLongitudeMasuk(BigDecimal longitudeMasuk) {
        this.longitudeMasuk = longitudeMasuk;
    }

    public String getLokasiPulang() {
        return lokasiPulang;
    }

    public void setLokasiPulang(String lokasiPulang) {
        this.lokasiPulang = lokasiPulang;
    }

    public Time getJamPulang() {
        return jamPulang;
    }

    public void setJamPulang(Time jamPulang) {
        this.jamPulang = jamPulang;
    }

    public BigDecimal getLatitudePulang() {
        return latitudePulang;
    }

    public void setLatitudePulang(BigDecimal latitudePulang) {
        this.latitudePulang = latitudePulang;
    }

    public BigDecimal getLongitudePulang() {
        return longitudePulang;
    }

    public void setLongitudePulang(BigDecimal longitudePulang) {
        this.longitudePulang = longitudePulang;
    }

    public String getCatatanTerlambat() {
        return catatanTerlambat;
    }

    public void setCatatanTerlambat(String catatanTerlambat) {
        this.catatanTerlambat = catatanTerlambat;
    }

    public Integer getTotalJamKerja() {
        return totalJamKerja;
    }

    public void setTotalJamKerja(Integer totalJamKerja) {
        this.totalJamKerja = totalJamKerja;
    }

    public Integer getLembur() {
        return lembur;
    }

    public void setLembur(Integer lembur) {
        this.lembur = lembur;
    }

    // Override hashCode() and equals() methods as needed
}

