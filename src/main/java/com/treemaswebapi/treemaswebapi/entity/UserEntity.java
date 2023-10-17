package com.treemaswebapi.treemaswebapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date; // Import java.util.Date untuk mendukung tanggal lahir
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.treemaswebapi.treemaswebapi.entity.UserRole.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usermaster", schema = "public")
@EntityListeners(UserEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "nik") // Anda dapat menentukan nama kolom secara eksplisit jika berbeda
    private String nik;

    @Column(name = "nama_karyawan")
    private String namaKaryawan; // Ganti nama variabel untuk mengikuti konvensi penamaan Java (camelCase)

    @Column(name = "no_ktp")
    private String noKtp;

    @Column(name = "no_npwp")
    private String noNpwp;

    @Column(name = "no_telepon")
    private String noTelepon;

    @Column(name = "email")
    private String email;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;

    @Column(name = "gol_darah")
    private String golDarah;

    @Column(name = "status_nikah")
    private String statusNikah;

    @Column(name = "agama")
    private String agama;

    @Column(name = "jenjang_pendidikan")
    private String jenjangPendidikan;

    @Column(name = "no_rekening")
    private String noRekening;

    @Column(name = "kewarganegaraan")
    private String kewarganegaraan;

    @Column(name = "alamat_ktp", length = 1000) // Sesuaikan panjangnya sesuai dengan kebutuhan Anda
    private String alamatKtp;

    @Column(name = "alamat_sekarang", length = 1000)
    private String alamatSekarang;

    @Column(name = "kode_pos")
    private String kodePos;

    @Column(name = "kontak_darurat")
    private String kontakDarurat;

    @Column(name = "status_kontak")
    private String statusKontak;

    @Column(name = "alamat_kontak", length = 1000)
    private String alamatKontak;

    @Column(name = "telepon_darurat")
    private String teleponDarurat;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "tanggal_gabung")
    private Date tanggalGabung; // Gunakan tipe Date untuk tanggal gabung

    @Column(name = "hak_cuti")
    private Integer hakCuti;

    @Column(name = "jenis_karyawan")
    private String jenisKaryawan;

    
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return nik;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
