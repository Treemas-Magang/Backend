package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treemaswebapi.treemaswebapi.entity.SysUserEntity;

public interface SysUserRepository extends JpaRepository<SysUserEntity, String> {
}
