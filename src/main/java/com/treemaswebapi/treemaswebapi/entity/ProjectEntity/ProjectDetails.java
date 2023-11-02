package com.treemaswebapi.treemaswebapi.entity.ProjectEntity;

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

        // Getters and setters
    }