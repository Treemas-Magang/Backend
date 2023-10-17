package com.treemaswebapi.treemaswebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treemaswebapi.treemaswebapi.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // UserEntity findByNik(String nik);
    // long countByNik(String nik);
    Optional<UserEntity> findByNik(String nik);
}
