
package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

import lombok.RequiredArgsConstructor;

@Repository
public interface AbsenAppRepository extends JpaRepository<AbsenAppEntity, Long> {
    
    List<AbsenAppEntity> findAll();

    @Query
    ("SELECT a FROM AbsenAppEntity a WHERE a.projectId = :projectId")
    List<AbsenAppEntity> findAllByProjectId(@Param("projectId") ProjectEntity projectId);
}