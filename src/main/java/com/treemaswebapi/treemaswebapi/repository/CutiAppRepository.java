package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiAppEntity;

@Repository
public interface CutiAppRepository extends JpaRepository<CutiAppEntity, Long> {
}
