
package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.AbsenEntity.AbsenAppEntity;

import lombok.RequiredArgsConstructor;

@Repository
public interface AbsenAppRepository extends JpaRepository<AbsenAppEntity, Long> {
    
    List<AbsenAppEntity> findAll();
}