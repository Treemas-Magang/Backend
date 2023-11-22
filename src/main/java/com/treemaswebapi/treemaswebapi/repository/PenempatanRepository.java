package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;


@Repository
public interface PenempatanRepository extends JpaRepository<PenempatanEntity, Long> {

    List<Long> findIdByNik(String nik);

    List<PenempatanEntity> findAllByNik(String nik);

     @Query("SELECT p FROM PenempatanEntity p WHERE p.nik = :nik")
    List<PenempatanEntity> findByNik(@Param("nik") String nik);

    Optional<PenempatanEntity> findByNikAndActive(String nik, String string);

    @Query("SELECT p FROM PenempatanEntity p WHERE p.projectId = :projectId AND p.nik = :nik")
    PenempatanEntity findActiveByProjectIdAndNik(@Param("projectId")ProjectEntity project,@Param("nik") String nik);

}