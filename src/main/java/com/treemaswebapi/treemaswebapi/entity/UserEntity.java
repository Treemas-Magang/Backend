package com.treemaswebapi.treemaswebapi.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Table;
import java.util.Date; // Import java.util.Date untuk mendukung tanggal lahir
import java.util.List;

@Entity
@Table(name = "usermaster", schema = "public")
public class UserEntity {

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
    private Date tanggalLahir; // Gunakan tipe Date untuk tanggal lahir

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

    @Column(name = "android_id")
    private String androidId;

    @Column(name = "tanggal_gabung")
    private Date tanggalGabung; // Gunakan tipe Date untuk tanggal gabung

    @Column(name = "hak_cuti")
    private int hakCuti;

    @Column(name = "jenis_karyawan")
    private String jenisKaryawan;

    @ElementCollection
    @CollectionTable(name = "user_list_member", joinColumns = @JoinColumn (name = "nik"))
    @Column(name = "list_member")
    private List<Integer> listMember; // biar bisa ngisi nama orang banyak dalem sini

    @Column(name = "leader")
    private Boolean leader; // Ganti dengan tipe Boolean untuk kolom boolean
    
    @Column(name = "password")
    private String password; // Ganti dengan tipe Boolean untuk kolom boolean

    // Constructor
    public UserEntity(String nik, String namaKaryawan, String noKtp, String noNpwp, String noTelepon,
                      String email, String tempatLahir, Date tanggalLahir, String jenisKelamin,
                      String golDarah, String statusNikah, String agama, String jenjangPendidikan,
                      String noRekening, String kewarganegaraan, String alamatKtp, String alamatSekarang,
                      String kodePos, String kontakDarurat, String statusKontak, String alamatKontak,
                      String teleponDarurat, String androidId, Date tanggalGabung, int hakCuti,
                      String jenisKaryawan,  List<Integer> listMember, Boolean leader, String password) {
                        
        this.nik = nik;
        this.namaKaryawan = namaKaryawan;
        this.noKtp = noKtp;
        this.noNpwp = noNpwp;
        this.noTelepon = noTelepon;
        this.email = email;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golDarah = golDarah;
        this.statusNikah = statusNikah;
        this.agama = agama;
        this.jenjangPendidikan = jenjangPendidikan;
        this.noRekening = noRekening;
        this.kewarganegaraan = kewarganegaraan;
        this.alamatKtp = alamatKtp;
        this.alamatSekarang = alamatSekarang;
        this.kodePos = kodePos;
        this.kontakDarurat = kontakDarurat;
        this.statusKontak = statusKontak;
        this.alamatKontak = alamatKontak;
        this.teleponDarurat = teleponDarurat;
        this.androidId = androidId;
        this.tanggalGabung = tanggalGabung;
        this.hakCuti = hakCuti;
        this.jenisKaryawan = jenisKaryawan;
        this.listMember = listMember;
        this.leader = leader;
        this.password = password;
    }

    public UserEntity(){

    }

     // Getter and Setter methods
     public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoNpwp() {
        return noNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        this.noNpwp = noNpwp;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getStatusNikah() {
        return statusNikah;
    }

    public void setStatusNikah(String statusNikah) {
        this.statusNikah = statusNikah;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJenjangPendidikan() {
        return jenjangPendidikan;
    }

    public void setJenjangPendidikan(String jenjangPendidikan) {
        this.jenjangPendidikan = jenjangPendidikan;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getAlamatKtp() {
        return alamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        this.alamatKtp = alamatKtp;
    }

    public String getAlamatSekarang() {
        return alamatSekarang;
    }

    public void setAlamatSekarang(String alamatSekarang) {
        this.alamatSekarang = alamatSekarang;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getKontakDarurat() {
        return kontakDarurat;
    }

    public void setKontakDarurat(String kontakDarurat) {
        this.kontakDarurat = kontakDarurat;
    }

    public String getStatusKontak() {
        return statusKontak;
    }

    public void setStatusKontak(String statusKontak) {
        this.statusKontak = statusKontak;
    }

    public String getAlamatKontak() {
        return alamatKontak;
    }

    public void setAlamatKontak(String alamatKontak) {
        this.alamatKontak = alamatKontak;
    }

    public String getTeleponDarurat() {
        return teleponDarurat;
    }

    public void setTeleponDarurat(String teleponDarurat) {
        this.teleponDarurat = teleponDarurat;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public Date getTanggalGabung() {
        return tanggalGabung;
    }

    public void setTanggalGabung(Date tanggalGabung) {
        this.tanggalGabung = tanggalGabung;
    }

    public int getHakCuti() {
        return hakCuti;

    }

    public void setHakCuti(int hakCuti) {
        this.hakCuti = hakCuti;
    }

    public String getJenisKaryawan() {
        return jenisKaryawan;
    }

    public void setJenisKaryawan(String jenisKaryawan) {
        this.jenisKaryawan = jenisKaryawan;
    }

    public List<Integer> getListMember() {
        return listMember;
    }

    public void setListMember(List<Integer> listMember) {
        this.listMember = listMember;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String leader) {
        this.password = password;
    }
}
