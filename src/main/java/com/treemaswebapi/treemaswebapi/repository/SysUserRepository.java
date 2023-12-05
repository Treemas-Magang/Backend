package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.JabatanEntity.JabatanEntity;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;

@Repository
public interface SysUserRepository extends JpaRepository<SysUserEntity, String> {
    Optional<SysUserEntity> findByUserId(String userid);
    Optional<SysUserEntity> findByEmail(String email);

    @Query("SELECT u FROM SysUserEntity u WHERE u.role.jabatanId = :jabatanId")
    List<SysUserEntity> findByRole(@Param("jabatanId") String jabatanId);
}
