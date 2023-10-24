package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity;

@Repository
public interface KaryawanRepository extends JpaRepository<KaryawanEntity, String> {
}
