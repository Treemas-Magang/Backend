package com.treemaswebapi.treemaswebapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.KaryawanEntity.KaryawanEntity;

@Repository
public interface KaryawanRepository extends JpaRepository<KaryawanEntity, String> {
    Optional<KaryawanEntity> findByNik(String nik);

    List<KaryawanEntity> findNamaByNiks(String nik);

    String findNamaByNik(String nik);


}
