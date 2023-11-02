package com.treemaswebapi.treemaswebapi.entity.PenempatanEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

// project_id FK dari tbl_project

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
    private String ustUpd;

    @Column(name = "dtmupd")
    private Timestamp dtmUpd;

    @Column(name = "active")
    private String active;

    public String getProjectId(String projectId) {
        return projectId;
    }

}