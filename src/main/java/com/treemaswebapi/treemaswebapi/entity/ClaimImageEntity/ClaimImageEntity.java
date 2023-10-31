package com.treemaswebapi.treemaswebapi.entity.ClaimImageEntity;


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
@Table(name = "tbl_claim_image", schema = "public")
public class ClaimImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "id_claim", length = 50)
    private String idClaim;

    @Column(name = "image_64")
    private String image64;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;
}