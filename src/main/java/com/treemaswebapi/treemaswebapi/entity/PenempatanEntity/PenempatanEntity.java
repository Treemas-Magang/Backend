package com.treemaswebapi.treemaswebapi.entity.PenempatanEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_penempatan", schema = "public")
public class PenempatanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "nik")
    private String nik;

    @Column(name = "usrupd")
    private String usrupd;

    @Column(name = "dtmupd")
    private Timestamp dtmupd;

    @Column(name = "active")
    private String active;

}