package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.UserLocationHistoryEntity.UserLocationHistoryEntity;

@Repository
public interface UserLocationHistoryRepository extends JpaRepository<UserLocationHistoryEntity, String> {
}