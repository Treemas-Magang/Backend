package com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenTrackingData;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenTrackingData {
    private List<Double> gpsLatitudeMsk;
    private Double gpsLatitudePlg;
    private List<Double> gpsLongitudeMsk;
    private Double gpsLongitudePlg;
    private LocalTime jamMsk;
}
