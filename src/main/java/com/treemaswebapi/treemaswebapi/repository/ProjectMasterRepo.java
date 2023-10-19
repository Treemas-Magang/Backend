package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.ProjectMaster;

@Repository
public interface ProjectMasterRepo extends JpaRepository<ProjectMaster, String> {
    String findByKodeProject(String kodeProject);
    String findNamaProjectByKodeProject(String kodeProject);
    String findAlamatProjectByKodeProject(String kodeProject);
}
