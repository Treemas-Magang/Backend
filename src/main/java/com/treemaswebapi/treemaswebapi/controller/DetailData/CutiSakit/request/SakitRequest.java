package com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SakitRequest {
    
    private String image;
    private Date tglMulai;
    private Date tglSelesai;
    private Date tglKembaliKerja;
    private BigDecimal jmlCuti;
    private String keperluanCuti;
}
