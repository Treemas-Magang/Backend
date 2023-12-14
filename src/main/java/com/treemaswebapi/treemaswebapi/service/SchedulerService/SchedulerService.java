package com.treemaswebapi.treemaswebapi.service.SchedulerService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;
import com.treemaswebapi.treemaswebapi.repository.AbsenRepository;
import com.treemaswebapi.treemaswebapi.repository.KaryawanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final KaryawanRepository karyawanRepository;
    private final AbsenRepository absenRepository;

    @Scheduled(cron = "0 0 5 * * ?")
    public void createAbsenEntries() {
        List<KaryawanEntity> dataKaryawan = karyawanRepository.findAll();
        for (KaryawanEntity karyawan : dataKaryawan) {
            AbsenEntity absenEntity = new AbsenEntity();
            absenEntity.setNik(karyawan.getNik());
            absenEntity.setDtmCrt(Timestamp.valueOf(LocalDateTime.now()));
            absenRepository.save(absenEntity);
            System.out.println("scheduled job has been executed");
        }
    }
}