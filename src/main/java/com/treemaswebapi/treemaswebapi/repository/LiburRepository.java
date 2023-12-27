package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.LiburEntity.LiburEntity;

@Repository
public interface LiburRepository extends JpaRepository<LiburEntity, Long> {

    @Query("SELECT COUNT(l) FROM Libur l WHERE l.isCutibersama = '1'")
    Long countIsCutibersama();
}
