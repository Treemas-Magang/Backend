package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenPulangAppEntity;

@Repository
public interface AbsenPulangAppRepository extends JpaRepository<AbsenPulangAppEntity, String> {
}

