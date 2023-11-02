package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    ProjectEntity findByProjectId(String projectId);
}