package com.treemaswebapi.treemaswebapi.controller.DetailData.CutiSakit.request;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CutiRequest {
    private Date tglMulai;
    private Date tglSelesai;
    private Date tglKembaliKerja;
    private String keperluanCuti;
    private String alamatCuti;

}
