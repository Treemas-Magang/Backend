package com.treemaswebapi.treemaswebapi.entity.UploadApkEntity;

import java.sql.Timestamp;

import org.postgresql.util.PGobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "upload_apk", schema = "public")
public class UploadApkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "apk_data", columnDefinition = "bytea")
    private PGobject apkData;
    
    @Column(name = "dtmcrt")
    private Timestamp dtmCrt;
}
