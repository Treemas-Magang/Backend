package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;

@Repository
public interface PenempatanRepository extends JpaRepository<PenempatanEntity, Long> {
}