package com.treemaswebapi.treemaswebapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.GeneralEntity.GeneralParamApprovalEntity;

@Repository
public interface GeneralParamApprovalRepository extends JpaRepository<GeneralParamApprovalEntity, String> {

    
}
