package com.treemaswebapi.treemaswebapi.entity.ClaimEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_claim", schema = "public")
public class ClaimEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 15)
    private String nik;

    @Column(name = "nama", length = 150)
    private String nama;

    @Column(name = "hari", length = 50)
    private String hari;

    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @Column(name = "id_claim", length = 50)
    private String idClaim;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;
}