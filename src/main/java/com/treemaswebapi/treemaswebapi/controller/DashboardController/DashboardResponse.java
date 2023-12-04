package com.treemaswebapi.treemaswebapi.controller.DashboardController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DashboardResponse {
    int totalCuti;
    int totalMasuk;
    int totalPulangCepat;
    int totalSakit;
    int totalTelatMasuk;
    int totalTidakMasuk;
}