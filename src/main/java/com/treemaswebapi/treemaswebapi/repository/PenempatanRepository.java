package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;


@Repository
public interface PenempatanRepository extends JpaRepository<PenempatanEntity, Long> {

    List<Long> findIdByNik(String nik);

    @Query("SELECT p.projectId FROM PenempatanEntity p WHERE p.nik = :nik")
    List<ProjectEntity> findProjectIdByNik(String nik);

    @Query("SELECT p FROM PenempatanEntity p WHERE p.nik = :nik")
    List<PenempatanEntity> findAllByNik(@Param("nik") String nik);
}