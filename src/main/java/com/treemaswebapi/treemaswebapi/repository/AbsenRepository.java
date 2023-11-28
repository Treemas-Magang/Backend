package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;

@Repository
public interface AbsenRepository extends JpaRepository<AbsenEntity, Long> {
    int countByIsAbsenAndNik(String isAbsen, String nik);
    int countByIsSakitAndNik(String isSakit, String nik);
    int countByNoteTelatMskIsNotNullAndNik(String nik);
    int countByNotePlgCepatIsNotNullAndNik(String nik);
    int countByIsCutiAndNik(String isCuti, String nik);
    int countByJamMskIsNullAndJamPlgIsNullAndNik(String nik);
}
