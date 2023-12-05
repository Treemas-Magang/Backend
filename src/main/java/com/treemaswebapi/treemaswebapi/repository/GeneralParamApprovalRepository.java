package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.GeneralEntity.GeneralParamApprovalEntity;
import com.treemaswebapi.treemaswebapi.entity.ProjectEntity.ProjectEntity;

@Repository
public class GeneralParamApprovalRepository {

    public List<GeneralParamApprovalEntity> findAll() {
        return null;
    }

    public List<GeneralParamApprovalEntity> findAllByProjectId(ProjectEntity projectId) {
        return null;
    }   
}
