package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.TimesheetEntity.TimesheetEntity;

@Repository
public interface TimesheetRepository extends JpaRepository<TimesheetEntity, String> {
}