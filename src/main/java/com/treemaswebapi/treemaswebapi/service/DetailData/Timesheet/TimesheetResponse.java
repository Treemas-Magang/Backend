package com.treemaswebapi.treemaswebapi.service.DetailData.Timesheet;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

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
    LocalDate tglAbsen;
    String projectId;
    Time jamMsk;
    Time jamPlg;
    BigDecimal totalJamKerja;
    BigDecimal overtime;
    String notePekerjaan;
}
