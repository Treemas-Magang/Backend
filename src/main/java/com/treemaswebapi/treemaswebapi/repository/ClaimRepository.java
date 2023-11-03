package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.ClaimEntity.ClaimEntity;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
}
