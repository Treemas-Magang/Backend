package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;

@Repository
public interface ProjectMasterRepo extends JpaRepository<ProjectMaster, String> {
    String findAllByKodeProject(String kodeProject);
    ProjectMaster findByKodeProject(String kodeProject);
    ProjectMaster findNamaProjectByKodeProject(String kodeProject);
    ProjectMaster findAlamatProjectByKodeProject(String kodeProject);
}
