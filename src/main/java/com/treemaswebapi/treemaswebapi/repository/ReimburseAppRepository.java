package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;
import com.treemaswebapi.treemaswebapi.entity.ReimburseEntity.ReimburseAppEntity;


@Repository
public interface ReimburseAppRepository extends JpaRepository<ReimburseAppEntity, Long> {

    List<ReimburseAppEntity> findAllByProjectId(ProjectEntity projectId);

    List<ReimburseAppEntity> findAllByNik(String nik);

    List<ReimburseAppEntity> findAllByProjectIdAndIsApproveIsNull(ProjectEntity projectId);
}