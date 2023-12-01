package com.treemaswebapi.treemaswebapi.controller.AbsenController;


import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AbsenResponse {
    AbsenEntity absenEntity;
    String absenImg;
    // buat response unprocessedAbsenEntity
    
}
