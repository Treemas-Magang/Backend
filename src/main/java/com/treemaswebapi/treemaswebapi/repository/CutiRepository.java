package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.CutiEntity.CutiEntity;

@Repository
public interface CutiRepository extends JpaRepository<CutiEntity, Long> {
    List<CutiEntity> findByFlagApp(String flagApp);
}