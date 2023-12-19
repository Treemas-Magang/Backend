package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.TimesheetEntity.TimesheetEntity;

@Repository
public interface TimesheetRepository extends JpaRepository<TimesheetEntity, Long> {

    List<TimesheetEntity> findAllByNik(String nik);
}