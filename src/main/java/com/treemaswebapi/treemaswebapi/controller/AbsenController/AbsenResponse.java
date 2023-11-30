package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

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
