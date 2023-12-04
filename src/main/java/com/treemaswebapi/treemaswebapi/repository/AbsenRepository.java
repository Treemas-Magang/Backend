package com.treemaswebapi.treemaswebapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

@Repository
public interface AbsenRepository extends JpaRepository<AbsenEntity, Long> {

    @Query("SELECT a FROM AbsenEntity a WHERE a.id = :id")
    AbsenEntity findByIdAbsen(Long id);

    List<AbsenEntity> findAllByProjectId(ProjectEntity projectId);

    List<AbsenEntity> findByNikAndTglAbsen(String nik, LocalDate currentDate);

    List<AbsenEntity> findAllByProjectIdAndTglAbsen(ProjectEntity projectId, LocalDate targetDate);

    List<AbsenEntity> findIdAbsenByNikAndIsAbsenIsNull(String nik);

    

    int countByIsAbsenAndNik(String string, String nik);

    int countByIsSakitAndNik(String string, String nik);

    int countByNoteTelatMskIsNotNullAndNik(String nik);

    int countByNotePlgCepatIsNotNullAndNik(String nik);

    int countByIsCutiAndNik(String string, String nik);

    int countByJamMskIsNullAndJamPlgIsNullAndNik(String nik);

    int countByJamMskIsNotNullAndNik(String nik);

    List<AbsenEntity> findIdAbsenByIsCuti(String string);

    List<AbsenEntity> findIdAbsenByIsCutiAndTglAbsen(String string, LocalDate date);

}
