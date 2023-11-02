package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.PenempatanEntity.PenempatanEntity;


@Repository
public interface PenempatanRepository extends JpaRepository<PenempatanEntity, Long> {

    List<Long> findIdByNik(String nik);

    List<String> findProjectIdByNik(String nik);
}