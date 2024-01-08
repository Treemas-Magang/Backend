package com.treemaswebapi.treemaswebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.UploadApkEntity.UploadApkEntity;

@Repository
public interface UploadApkRepository extends JpaRepository<UploadApkEntity, Long> {

    Optional<UploadApkEntity> findByFileName(String filename);
    Optional<UploadApkEntity> findTopByOrderByDtmCrtDesc();

}
