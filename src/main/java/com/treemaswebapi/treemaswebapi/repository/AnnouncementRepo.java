package com.treemaswebapi.treemaswebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treemaswebapi.treemaswebapi.entity.Announcement;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Integer> {
    Optional<Announcement> findByIdAnn(int idAnn);
}
