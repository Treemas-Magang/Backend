package com.treemaswebapi.treemaswebapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

@Repository
public interface AbsenRepository extends JpaRepository<AbsenEntity, Long> {
    String findByProjectId(ProjectEntity projectId);

    List<AbsenEntity> findAllByProjectId(ProjectEntity projectId);

    List<AbsenEntity> findByNikAndTglAbsen(String nik, LocalDate currentDate);

    List<AbsenEntity> findAllByProjectIdAndTglAbsen(ProjectEntity projectId, LocalDate targetDate);
}
