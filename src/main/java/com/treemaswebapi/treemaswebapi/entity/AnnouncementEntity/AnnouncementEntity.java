package com.treemaswebapi.treemaswebapi.entity.AnnouncementEntity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tbl_announcement", schema = "public")
public class AnnouncementEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "header")
    private String header;

    @Column(name = "note")
    private String note;

    @Column(name = "tgl_upload")
    private Instant tglUpload;

    @Column(name = "image")
    private String image;

    @Column(name = "image_64")
    private String image64;

    @Column(name = "footer")
    private String footer;

    @Column(name = "usrcrt")
    private String usrCrt;
}