package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

@Repository
public interface AbsenAppUploadRepository extends JpaRepository<AbsenAppUploadEntity, Long> {

    List<AbsenAppUploadEntity> findAllByProjectId(ProjectEntity projectId);
}