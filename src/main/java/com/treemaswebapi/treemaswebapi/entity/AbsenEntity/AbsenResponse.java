package com.treemaswebapi.treemaswebapi.entity.AbsenEntity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public class AbsenResponse {
        private String idAbsen;
        private String projectId;
        private String projectAdress; 
        private String nik; 
        private String nama;
        private String tglAbsen;


        // Getters and setters
    }