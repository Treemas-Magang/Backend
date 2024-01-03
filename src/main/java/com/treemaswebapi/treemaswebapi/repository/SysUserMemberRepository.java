package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserMemberEntity;

@Repository
public interface SysUserMemberRepository extends JpaRepository<SysUserMemberEntity, Long> {
    List<SysUserMemberEntity> findByNikLeader(String nikLeader);
    void deleteByNikUserAndNikLeader(String nikUser, String nikLeader);
}