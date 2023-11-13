package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppUploadEntity;

@Repository
public interface AbsenAppUploadRepository extends JpaRepository<AbsenAppUploadEntity, Long> {
}