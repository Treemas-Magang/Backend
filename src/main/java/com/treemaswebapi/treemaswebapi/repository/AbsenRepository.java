package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenEntity;

@Repository
public interface AbsenRepository extends JpaRepository<AbsenEntity, Long> {
    List<AbsenEntity> findByProjectId(String projectId);
}
