package com.treemaswebapi.treemaswebapi.entity.TipeClaimEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_tipe_claim", schema = "public")
public class TipeClaimEntity implements Serializable {

    @Id
    @Column(name = "id_claim")
    private Long idClaim;

    @Column(name = "nama_claim", length = 150)
    private String namaClaim;

    @Column(name = "value_claim", precision = 18, scale = 0)
    private BigDecimal valueClaim;

    @Column(name = "keterangan", length = 255)
    private String keterangan;

    @Column(name = "usrcrt", length = 50)
    private String usrcrt;

    @Column(name = "dtmcrt")
    private Timestamp dtmcrt;

    @Column(name = "usrupd", length = 50)
    private String usrupd;

    @Column(name = "dtmupd")
    private Timestamp dtmupd;
}
