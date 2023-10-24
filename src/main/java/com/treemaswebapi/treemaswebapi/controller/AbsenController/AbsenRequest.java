package com.treemaswebapi.treemaswebapi.controller.AbsenController;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbsenRequest {
    //yang diminta
    private String kodeProject;
    private String lokasiProject;
    private LocalTime waktu;
    private String lokasiSekarang;
    private int jarak;
    private byte[] absenImg;
    private String catatanTerlambat;
}