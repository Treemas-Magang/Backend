package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiImageEntity;
@Repository
public interface CutiImageRepository extends JpaRepository<CutiImageEntity, Long> {
}
 