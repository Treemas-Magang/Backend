package com.treemaswebapi.treemaswebapi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.treemaswebapi.treemaswebapi.repository.AbsenMasterRepo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table
public class AbsenMaster implements AbsenMasterRepo{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absen_master_id_absen_seq")
    @SequenceGenerator(name = "absen_master_id_absen_seq", sequenceName = "absen_master_id_absen_seq", allocationSize = 1)
    @Column(name = "id_absen")
    public int idAbsen;

    @ManyToOne
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    private UserEntity user;

    @Column(name = "tanggal_absen")
    private LocalDate tanggalAbsen;

    @Column(name = "hari_absen")
    private String hariAbsen;

    @ManyToOne
    @JoinColumn(name = "kode_project", referencedColumnName = "kode_project")
    private ProjectMaster project;

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

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDate getTanggalAbsen() {
        return tanggalAbsen;
    }

    public void setTanggalAbsen(LocalDate tanggalAbsen) {
        this.tanggalAbsen = tanggalAbsen;
    }

    public String getHariAbsen() {
        return hariAbsen;
    }

    public void setHariAbsen(String hariAbsen) {
        this.hariAbsen = hariAbsen;
    }

    public ProjectMaster getProject() {
        return project;
    }

    public void setProject(ProjectMaster project) {
        this.project = project;
    }

    public String getLokasiMasuk() {
        return lokasiMasuk;
    }

    public void setLokasiMasuk(String lokasiMasuk) {
        this.lokasiMasuk = lokasiMasuk;
    }

    public LocalTime getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(LocalTime jamMasuk) {
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

    public Short getTotalJamKerja() {
        return totalJamKerja;
    }

    public void setTotalJamKerja(Short totalJamKerja) {
        this.totalJamKerja = totalJamKerja;
    }

    public boolean isLembur() {
        return lembur;
    }

    public void setLembur(boolean lembur) {
        this.lembur = lembur;
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllByIdInBatch'");
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public void deleteAllInBatch(Iterable<AbsenMaster> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public <S extends AbsenMaster> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends AbsenMaster> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flush'");
    }

    @Override
    public AbsenMaster getById(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public AbsenMaster getOne(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public AbsenMaster getReferenceById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferenceById'");
    }

    @Override
    public <S extends AbsenMaster> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllAndFlush'");
    }

    @Override
    public <S extends AbsenMaster> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAndFlush'");
    }

    @Override
    public List<AbsenMaster> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<AbsenMaster> findAllById(Iterable<String> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    @Override
    public <S extends AbsenMaster> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void delete(AbsenMaster entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAll(Iterable<? extends AbsenMaster> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Optional<AbsenMaster> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public <S extends AbsenMaster> S save(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<AbsenMaster> findAll(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Page<AbsenMaster> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends AbsenMaster> long count(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public <S extends AbsenMaster> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    @Override
    public <S extends AbsenMaster> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends AbsenMaster, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }

    @Override
    public <S extends AbsenMaster> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public AbsenMaster findByIdAbsen(int idAbsen) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByIdAbsen'");
    }

    // Getters and setters
}

