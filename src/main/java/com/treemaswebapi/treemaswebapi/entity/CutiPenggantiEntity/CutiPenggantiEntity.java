package com.treemaswebapi.treemaswebapi.entity.CutiPenggantiEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Entity
@Table(name = "tbl_cuti_pengganti", schema = "public")
public class CutiPenggantiEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "tgl_dapat")
    private Date tglDapat;

    @Column(name = "jml_cuti")
    private BigDecimal jmlCuti;

    @Column(name = "note_cuti_pengganti")
    private String noteCutiPengganti;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "is_exp", length = 1)
    private String isExp;
}