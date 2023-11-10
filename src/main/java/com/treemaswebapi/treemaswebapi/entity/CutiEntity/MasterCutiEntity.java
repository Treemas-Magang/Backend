package com.treemaswebapi.treemaswebapi.entity.CutiEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Entity
@Table(name = "tbl_master_cuti", schema = "public")
public class MasterCutiEntity {
    
    @Id
    private String id;

    @Column(name = "cuti_desc")
    private String cutiDesc;
    
    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "usrcrt")
    private String usrCrt;

    @Column(name = "dtmcrt")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dtmCrt;

    @PrePersist
    protected void onCreate() {
        long currentTimeMillis = System.currentTimeMillis();
        // Mengatur tglUpload ke waktu saat ini tanpa fraksi detik
        dtmCrt = new Timestamp(currentTimeMillis - (currentTimeMillis % 1000));
    }
    
    @Column(name = "usrupd")
    private String usrUpd;

    @Column(name = "dtmupd")
    @UpdateTimestamp
    private Timestamp dtmUpd;
}
