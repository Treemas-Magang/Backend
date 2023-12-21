package com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;


import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TimesheetResponse {
    String nik;
    String namaKaryawan;
    String hari;
    Date tglAbsen;
    ProjectEntity projectId;
    Time jamMsk;
    Time jamPlg;
    BigDecimal totalJamKerja;
    int overtime;
    String notePekerjaan;
}
