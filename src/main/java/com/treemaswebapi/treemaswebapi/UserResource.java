package com.treemaswebapi.treemaswebapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, Object> userMap){
        try {
            String nik = (String) userMap.get("nik");
            String namaKaryawan = (String) userMap.get("nama_karyawan");
            String noKTP = (String) userMap.get("no_ktp");
            String noNPWP = (String) userMap.get("no_npwp");
            String noTelepon = (String) userMap.get("no_telepon");
            String email = (String) userMap.get("email");
            String tempatLahir = (String) userMap.get("tempat_lahir");
            String tanggalLahirStr = (String) userMap.get("tanggal_lahir");
            java.sql.Date tanggalLahir = java.sql.Date.valueOf(tanggalLahirStr);
            String jenisKelamin = (String) userMap.get("jenis_kelamin");
            String golDarah = (String) userMap.get("gol_darah");
            String statusNikah = (String) userMap.get("status_nikah");
            String agama = (String) userMap.get("agama");
            String jenjangPendidikan = (String) userMap.get("jenjang_pendidikan");
            String noRekening = (String) userMap.get("no_rekening");
            String kewarganegaraan = (String) userMap.get("kewarganegaraan");
            String alamatKTP = (String) userMap.get("alamat_ktp");
            String alamatSekarang = (String) userMap.get("alamat_sekarang");
            String kodePos = (String) userMap.get("kode_pos");
            String kontakDarurat = (String) userMap.get("kontak_darurat");
            String statusKontak = (String) userMap.get("status_kontak");
            String alamatKontak = (String) userMap.get("alamat_kontak");
            String teleponDarurat = (String) userMap.get("telepon_darurat");
            String androidId = (String) userMap.get("android_id");
            String tanggalGabungStr = (String) userMap.get("tanggal_gabung");
            java.sql.Date tanggalGabung = java.sql.Date.valueOf(tanggalGabungStr);
            String hakCuti = (String) userMap.get("hak_cuti");
            String jenisKaryawan = (String) userMap.get("jenis_karyawan");
            List<Integer> listMember = (List<Integer>) userMap.get("list_member");
            Integer[] listMemberArray = listMember.toArray(new Integer[0]);

            String leaderStr = String.valueOf((Boolean) userMap.get("leader"));
            
            // Lanjutkan dengan operasi pendaftaran atau penyimpanan data pengguna sesuai kebutuhan Anda
            // ...

            // Mengembalikan respons sukses jika pendaftaran berhasil
            return "Pendaftaran pengguna sukses"+listMember;
        } catch (Exception e) {
            // Tangani kesalahan jika ada, misalnya validasi data, penanganan kesalahan, dll.
            return "Pendaftaran pengguna gagal: " + e.getMessage();
        }
    }
}
