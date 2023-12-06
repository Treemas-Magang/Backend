package com.treemaswebapi.treemaswebapi.controller.AbsenController;


import java.util.List;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenTrackingEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AbsenResponse {
    List<AbsenTrackingEntity> absenTrackingEntities;
    String absenImg;
    // buat response unprocessedAbsenEntity
    
}
