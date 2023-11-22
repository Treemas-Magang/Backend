package com.treemaswebapi.treemaswebapi.entity.ProjectEntity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public class ProjectDetails {
        private String projectName;
        private String projectAddress;
        private String projectId;
        private String active;
        private String jrkMax;
        private Double gpsLongitude;
        private Double gpsLatitude;
        private Time jamMasuk;
        private Time jamKeluar;

        // Getters and setters
    }