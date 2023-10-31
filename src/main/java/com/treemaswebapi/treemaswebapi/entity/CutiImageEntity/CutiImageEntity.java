package com.treemaswebapi.treemaswebapi.entity.CutiImageEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Entity
@Table(name = "tbl_cuti_image", schema = "public")
public class CutiImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "tgl_mulai")
    private Date tglMulai;

    @Column(name = "flg_ket", length = 50)
    private String flgKet;

    @Column(name = "image")
    private String image;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrapp", length = 50)
    private String usrapp;

    @Column(name = "dtmapp")
    private Timestamp dtmapp;
}