// package com.treemaswebapi.treemaswebapi.entity;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;
// import java.util.function.Function;

// import org.springframework.data.domain.Example;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

// import com.treemaswebapi.treemaswebapi.repository.AbsenImageRepo;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.Lob;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.SequenceGenerator;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "absen_image")
// public class AbsenImageEntity {
//     @Id
//     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absen_image_id_absen_image_seq" )
//     @SequenceGenerator(name = "absen_image_id_absen_image_seq", sequenceName = "absen_image_id_absen_image_seq", allocationSize = 1)
//     @Column(name = "id_absen_image")
//     private int idAbsenImg;

//     @ManyToOne
//     @JoinColumn(name = "id_absen", referencedColumnName = "id_absen")
//     private AbsenMaster idAbsen;

//     @ManyToOne
//     @JoinColumn(name = "nik", referencedColumnName = "nik")
//     private UserEntity nik;

//     @Lob
//     @Column(name = "image")
//     private byte[] image;

//     @Column(name = "image_timestamp")
//     private LocalDateTime imageTimestamp;

//     public int getId() {
//         return idAbsenImg;
//     }

//     public void setIdAbsenImg(int idAbsenImg) {
//         this.idAbsenImg = idAbsenImg;
//     }

//     public AbsenMaster getIdAbsen() {
//         return idAbsen;
//     }

//     public void setIdAbsen(AbsenMaster idAbsen) {
//         this.idAbsen = idAbsen;
//     }

//     public UserEntity getNik() {
//         return nik;
//     }

//     public void setNik(UserEntity nik) {
//         this.nik = nik;
//     }

//     public byte[] getImage() {
//         return image;
//     }

//     public void setImage(byte[] image) {
//         this.image = image;
//     }

//     public LocalDateTime getImageTimestamp() {
//         return imageTimestamp;
//     }

//     public void setImageTimestamp(LocalDateTime imageTimestamp) {
//         this.imageTimestamp = imageTimestamp;
//     }

// }