package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectListRequest {
    private String nik;
    private String token;
}
