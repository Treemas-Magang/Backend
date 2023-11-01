package com.treemaswebapi.treemaswebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserMemberEntity;

@Repository
public interface SysUserMemberRepository extends JpaRepository<SysUserMemberEntity, String> {
}