package com.treemaswebapi.treemaswebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;

@Repository
public interface SysUserRepository extends JpaRepository<SysUserEntity, String> {
    Optional<SysUserEntity> findByUserid(String userid);
}
